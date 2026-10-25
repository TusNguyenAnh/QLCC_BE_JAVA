package com.mbs.qlcc.service.impl;

import com.mbs.qlcc.domain.Equipment;
import com.mbs.qlcc.dto.request.Equipment.CreateEquipmentRequest;
import com.mbs.qlcc.dto.request.Equipment.UpdateEquipmentRequest;
import com.mbs.qlcc.dto.response.Equipment.EquipmentResponse;
import com.mbs.qlcc.dto.response.Equipment.IEquipmentHistoryResponse;
import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.exception.AppException;
import com.mbs.qlcc.mapper.Equipment.EquipmentMapper;
import com.mbs.qlcc.repository.Equipment.IEquipmentRepository;
import com.mbs.qlcc.service.IEquipmentService;
import com.mbs.qlcc.utils.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EquipmentServiceImpl implements IEquipmentService {

    IEquipmentRepository equipmentRepository;

    @Override
    @Transactional
    public EquipmentResponse create(CreateEquipmentRequest request, String complexId) {
        validateRequired(request.getEquipmentCode(), request.getEquipmentName(), request.getCategory());

        String code = request.getEquipmentCode().trim();
        if (equipmentRepository.existsByEquipmentCodeAndComplexIdAndDeletedAtIsNull(code, complexId)) {
            throw new AppException(ErrorCode.EQUIPMENT_CODE_EXISTED);
        }

        Equipment saved = equipmentRepository.save(EquipmentMapper.toEntity(request, complexId));
        return EquipmentMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public EquipmentResponse findById(String id, String complexId) {
        Equipment equipment = equipmentRepository.findByIdAndComplexIdAndDeletedAtIsNull(id, complexId)
                .orElseThrow(() -> new AppException(ErrorCode.EQUIPMENT_NOT_FOUND));
        return EquipmentMapper.toResponse(equipment);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<EquipmentResponse> search(String complexId, String keyword, String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        String kw = (keyword == null || keyword.isBlank()) ? null : keyword.trim();
        String cat = (category == null || category.isBlank()) ? null : category.trim();

        Page<Equipment> result = equipmentRepository.search(complexId, kw, cat, pageable);

        return new PageResponse<>(
                result.getContent().stream().map(EquipmentMapper::toResponse).toList(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    @Override
    @Transactional
    public EquipmentResponse update(String id, UpdateEquipmentRequest request, String complexId) {
        validateRequired(request.getEquipmentCode(), request.getEquipmentName(), request.getCategory());

        Equipment equipment = equipmentRepository.findByIdAndComplexIdAndDeletedAtIsNull(id, complexId)
                .orElseThrow(() -> new AppException(ErrorCode.EQUIPMENT_NOT_FOUND));

        String code = request.getEquipmentCode().trim();
        if (equipmentRepository.existsByEquipmentCodeAndComplexIdAndIdNotAndDeletedAtIsNull(code, complexId, id)) {
            throw new AppException(ErrorCode.EQUIPMENT_CODE_EXISTED);
        }

        equipment.setEquipmentCode(code);
        equipment.setEquipmentName(request.getEquipmentName().trim());
        equipment.setCategory(request.getCategory().trim());
        equipment.setDescription(request.getDescription());
        equipment.setUpdatedAt(Instant.now());

        return EquipmentMapper.toResponse(equipmentRepository.save(equipment));
    }

    @Override
    public List<IEquipmentHistoryResponse> getEquipmentHistory(String id, String complexId, String category) {
        return equipmentRepository.getEquipmentHistory(id, category, complexId);
    }

    @Override
    @Transactional
    public void delete(List<String> ids, String complexId) {
        if (ids == null || ids.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        List<String> distinctIds = ids.stream().distinct().toList();
        List<Equipment> equipments = equipmentRepository.findAllByIdInAndComplexIdAndDeletedAtIsNull(distinctIds, complexId);
        if (equipments.size() != distinctIds.size()) {
            throw new AppException(ErrorCode.EQUIPMENT_NOT_FOUND);
        }

        Instant now = Instant.now();
        equipments.forEach(e -> e.setDeletedAt(now));
        equipmentRepository.saveAll(equipments);
    }

    private void validateRequired(String code, String name, String category) {
        if (code == null || code.isBlank()) throw new AppException(ErrorCode.EQUIPMENT_CODE_NOT_EMPTY);
        if (name == null || name.isBlank()) throw new AppException(ErrorCode.EQUIPMENT_NAME_NOT_EMPTY);
        if (category == null || category.isBlank()) throw new AppException(ErrorCode.EQUIPMENT_CATEGORY_NOT_EMPTY);
    }
}
