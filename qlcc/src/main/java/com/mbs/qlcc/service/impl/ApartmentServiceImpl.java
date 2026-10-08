package com.mbs.qlcc.service.impl;

import com.mbs.qlcc.domain.Apartment;
import com.mbs.qlcc.domain.Building;
import com.mbs.qlcc.dto.request.Apartment.CreateApartmentRequest;
import com.mbs.qlcc.dto.request.Apartment.FilterApartmentRequest;
import com.mbs.qlcc.dto.request.Apartment.UpdateApartmentRequest;
import com.mbs.qlcc.dto.response.Apartment.ApartmentResponse;
import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.exception.AppException;
import com.mbs.qlcc.mapper.Apartment.ApartmentMapper;
import com.mbs.qlcc.repository.Apartment.IApartmentRepository;
import com.mbs.qlcc.repository.Building.IBuildingRepository;
import com.mbs.qlcc.repository.Specification.ApartmentSpecification;
import com.mbs.qlcc.service.IApartmentService;
import com.mbs.qlcc.utils.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApartmentServiceImpl implements IApartmentService {

    IApartmentRepository apartmentRepository;
    IBuildingRepository buildingRepository;

    @Override
    @Transactional
    public ApartmentResponse create(CreateApartmentRequest request, String complexId) {
        Building building = buildingRepository.findByIdAndDeletedAtIsNull(request.getBuildingId())
                .orElseThrow(() -> new AppException(ErrorCode.BUILDING_NOT_FOUND));

        boolean exists = apartmentRepository.existsByBuilding_IdAndAptNumberAndDeletedAtIsNull(request.getBuildingId(), request.getAptNumber());
        if (exists) {
            throw new AppException(ErrorCode.APT_NUMBER_EXISTED);
        }

        Apartment apartment = ApartmentMapper.toEntity(request, complexId, building);
        Apartment saved = apartmentRepository.save(apartment);

        return ApartmentMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ApartmentResponse findById(String id) {
        Apartment apartment = apartmentRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new AppException(ErrorCode.APARTMENT_NOT_FOUND));

        return ApartmentMapper.toResponse(apartment);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ApartmentResponse> findByBuildingId(String buildingId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Apartment> page = apartmentRepository.findByBuildingId(buildingId, pageable);

        return new PageResponse<>(
                page.getContent().stream().map(ApartmentMapper::toResponse).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ApartmentResponse> filterByStatus(int status, FilterApartmentRequest request) {
        Sort.Direction direction = (request.getOrder() != null && request.getOrder().equalsIgnoreCase("desc"))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(), direction, "createdAt");
        Specification<Apartment> spec = ApartmentSpecification.filterApartment(
                status,
                request.getKeyword(),
                request.getTimeRequestStart(),
                request.getTimeRequestEnd()
        );

        Page<Apartment> page = apartmentRepository.findAll(spec, pageable);

        return new PageResponse<>(
                page.getContent().stream().map(ApartmentMapper::toResponse).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ApartmentResponse> findByComplexId(String complexId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Apartment> page = apartmentRepository.findByComplexId(complexId, pageable);

        return new PageResponse<>(
                page.getContent().stream().map(ApartmentMapper::toResponse).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    @Override
    @Transactional
    public ApartmentResponse update(String id, UpdateApartmentRequest request) {
        Apartment apartment = apartmentRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new AppException(ErrorCode.APARTMENT_NOT_FOUND));

        String buildingId = apartment.getBuilding() != null ? apartment.getBuilding().getId() : null;

        if (request.getAptNumber() != null && !request.getAptNumber().equals(apartment.getAptNumber()) && buildingId != null) {
            boolean exists = apartmentRepository.existsByBuilding_IdAndAptNumberAndDeletedAtIsNull(buildingId, request.getAptNumber());
            if (exists) {
                throw new AppException(ErrorCode.APT_NUMBER_EXISTED);
            }
        }

        apartment.setFloor(request.getFloor());
        apartment.setAptNumber(request.getAptNumber());
        apartment.setGrossArea(request.getGrossArea());
        apartment.setCoefficient(request.getCoefficient());

        if (request.getGrossArea() != null && request.getCoefficient() != null) {
            apartment.setCarpetArea(request.getGrossArea().multiply(request.getCoefficient()));
        }

        apartment.setAptType(request.getAptType());
        apartment.setDescription(request.getDescription());
        apartment.setUpdatedAt(Instant.now());

        Apartment updated = apartmentRepository.save(apartment);

        return ApartmentMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public String importExcel(List<CreateApartmentRequest> request, String complexId) {
        List<Building> buildings = buildingRepository.findByComplexId(complexId);
        Map<String, Building> buildingByName = buildings.stream()
                .collect(Collectors.toMap(b -> b.getBuildingName().trim().toLowerCase(), b -> b, (b1, b2) -> b1));
        Map<String, Building> buildingById = buildings.stream()
                .collect(Collectors.toMap(Building::getId, b -> b, (b1, b2) -> b1));

        Set<String> notFoundBuildings = new HashSet<>();
        List<Apartment> apartments = new ArrayList<>();

        for (CreateApartmentRequest crq : request) {
            String key = crq.getBuildingId() != null ? crq.getBuildingId().trim() : "";
            Building building = buildingByName.get(key.toLowerCase());
            if (building == null) {
                  notFoundBuildings.add(key);
                continue;
            }

            apartments.add(ApartmentMapper.toEntity(crq, complexId, building));
        }

        if (!notFoundBuildings.isEmpty()) {
            return "Các tòa nhà sau không tồn tại: " + String.join(", ", notFoundBuildings);
        }

        // Validate duplicates per building
        Map<String, List<String>> apartmentsByBuilding = apartments.stream()
                .collect(Collectors.groupingBy(
                        a -> a.getBuilding().getId(),
                        Collectors.mapping(Apartment::getAptNumber, Collectors.toList())
                ));

        for (Map.Entry<String, List<String>> entry : apartmentsByBuilding.entrySet()) {
            String buildingId = entry.getKey();
            List<String> aptNumbers = entry.getValue();

            List<Apartment> existingApts = apartmentRepository.findByBuildingIdAndAptNumberIn(buildingId, aptNumbers);
            if (!existingApts.isEmpty()) {
                Building b = buildingById.get(buildingId);
                String bName = b != null ? b.getBuildingName() : buildingId;
                List<String> existingNumbers = existingApts.stream().map(Apartment::getAptNumber).toList();
                return "Trong tòa " + bName + ", các căn hộ sau đã tồn tại: " + String.join(", ", existingNumbers);
            }
        }

        apartmentRepository.saveAll(apartments);
        return "DONE";
    }
}
