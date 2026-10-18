package com.mbs.qlcc.service.impl;

import com.mbs.qlcc.domain.Brief;
import com.mbs.qlcc.domain.MediaFile;
import com.mbs.qlcc.dto.request.Brief.CreateBriefRequest;
import com.mbs.qlcc.dto.request.Brief.UpdateBriefRequest;
import com.mbs.qlcc.dto.response.Brief.BriefResponse;
import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.exception.AppException;
import com.mbs.qlcc.mapper.Brief.BriefMapper;
import com.mbs.qlcc.repository.Brief.IBriefRepository;
import com.mbs.qlcc.repository.Specification.BriefSpecification;
import com.mbs.qlcc.service.IBriefService;
import com.mbs.qlcc.service.IMediaFileService;
import com.mbs.qlcc.utils.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BriefServiceImpl implements IBriefService {

    IBriefRepository briefRepository;
    IMediaFileService mediaFileService;
    BriefMapper briefMapper;

    @Override
    @Transactional
    public BriefResponse create(CreateBriefRequest request, String complexId) throws IOException {
        if (complexId == null || complexId.isBlank()) {
            throw new AppException(ErrorCode.COMPLEX_ID_NOT_EMPTY);
        }

        String effectiveBriefName = request.getEffectiveBriefName();
        if (effectiveBriefName == null || effectiveBriefName.isBlank()) {
            throw new AppException(ErrorCode.BRIEF_NAME_NOT_EMPTY);
        }

        String type = request.type() != null ? request.type().trim() : "";
        if (type.isBlank()) {
            throw new AppException(ErrorCode.BRIEF_TYPE_NOT_EMPTY);
        }

        if (request.file() == null || request.file().isEmpty()) {
            throw new AppException(ErrorCode.FILE_REQUIRED);
        }

        // Kiểm tra với complexId và loại brief thì tên file đã tồn tại hay chưa (trừ các bản ghi đã xóa mềm)
        boolean exists = briefRepository.existsByComplexIdAndTypeAndBriefNameAndIsDeletedFalse(
                complexId, type, effectiveBriefName
        );
        if (exists) {
            log.warn("Brief with name '{}' and type '{}' already exists in complex '{}'", effectiveBriefName, type, complexId);
            throw new AppException(ErrorCode.BRIEF_NAME_EXISTED);
        }

        // Lưu thông tin vào bảng brief (chỉ các trường đã được định nghĩa trong entity Brief)
        Brief brief = Brief.builder()
                .complexId(complexId)
                .briefName(effectiveBriefName)
                .type(type)
                .isDeleted(false)
                .build();
        Brief savedBrief = briefRepository.save(brief);

        // Lưu file qua MediaFileService đã có
        mediaFileService.create(List.of(request.file()), "brief", savedBrief.getId());

        MediaFile mediaFile = mediaFileService.findFirstByOwner("brief", savedBrief.getId()).orElse(null);
        log.info("Created brief successfully with id: {}", savedBrief.getId());
        return briefMapper.toResponse(savedBrief, mediaFile);
    }

    @Override
    @Transactional
    public BriefResponse update(String id, UpdateBriefRequest request, String complexId) {
        if (complexId == null || complexId.isBlank()) {
            throw new AppException(ErrorCode.COMPLEX_ID_NOT_EMPTY);
        }

        Brief brief = briefRepository.findByIdAndComplexIdAndIsDeletedFalse(id, complexId)
                .orElseThrow(() -> new AppException(ErrorCode.BRIEF_NOT_FOUND));

        String effectiveBriefName = request.getEffectiveBriefName();
        if (effectiveBriefName == null || effectiveBriefName.isBlank()) {
            throw new AppException(ErrorCode.BRIEF_NAME_NOT_EMPTY);
        }

        String type = request.type() != null ? request.type().trim() : "";
        if (type.isBlank()) {
            throw new AppException(ErrorCode.BRIEF_TYPE_NOT_EMPTY);
        }

        // Kiểm tra với complexId và loại brief thì tên file sửa đổi đã tồn tại ở bản ghi khác chưa
        boolean existsOther = briefRepository.existsByComplexIdAndTypeAndBriefNameAndIdNotAndIsDeletedFalse(
                complexId, type, effectiveBriefName, id
        );
        if (existsOther) {
            throw new AppException(ErrorCode.BRIEF_NAME_EXISTED);
        }

        brief.setBriefName(effectiveBriefName);
        brief.setType(type);

        Brief updated = briefRepository.save(brief);
        MediaFile mediaFile = mediaFileService.findFirstByOwner("brief", updated.getId()).orElse(null);
        log.info("Updated brief successfully with id: {}", updated.getId());
        return briefMapper.toResponse(updated, mediaFile);
    }

    @Override
    @Transactional(readOnly = true)
    public BriefResponse findById(String id, String complexId) {
        Brief brief = briefRepository.findByIdAndComplexIdAndIsDeletedFalse(id, complexId)
                .orElseThrow(() -> new AppException(ErrorCode.BRIEF_NOT_FOUND));

        MediaFile mediaFile = mediaFileService.findFirstByOwner("brief", brief.getId()).orElse(null);
        return briefMapper.toResponse(brief, mediaFile);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<BriefResponse> filter(String complexId, String briefName, String type, Pageable pageable) {
        Specification<Brief> spec = BriefSpecification.filter(complexId, briefName, type);
        Page<Brief> pageResult = briefRepository.findAll(spec, pageable);

        List<String> briefIds = pageResult.getContent().stream().map(Brief::getId).toList();
        Map<String, MediaFile> mediaMap = mediaFileService.findMapByOwnerIds("brief", briefIds);

        List<BriefResponse> responses = pageResult.getContent()
                .stream()
                .map(b -> briefMapper.toResponse(b, mediaMap.get(b.getId())))
                .toList();

        return new PageResponse<>(
                responses,
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
    }

    @Override
    @Transactional
    public void delete(String id, String complexId) {
        Brief brief = briefRepository.findByIdAndComplexIdAndIsDeletedFalse(id, complexId)
                .orElseThrow(() -> new AppException(ErrorCode.BRIEF_NOT_FOUND));

        // 1. Thực hiện xóa mềm bản ghi brief
        brief.setDeleted(true);
        brief.setDeletedAt(Instant.now());
        briefRepository.save(brief);
        log.info("Soft deleted brief with id: {}", id);

        // 2. Xóa mềm media_files và xóa file vật lý trong disk qua MediaFileService
        mediaFileService.deleteByOwner("brief", id);
    }
}
