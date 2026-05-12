package com.mbs.qlcc.adapters.db.Apartment;

import com.mbs.qlcc.adapters.db.Building.BuildingDataMapper;
import com.mbs.qlcc.adapters.db.Specification.ApartmentSpecification;
import com.mbs.qlcc.entities.Apartment.Apartment;
import com.mbs.qlcc.usecases.request.Apartment.FilterApartmentInpRequest;
import com.mbs.qlcc.usecases.output.Apartment.IApartmentDsGateway;
import com.mbs.qlcc.usecases.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaApartment implements IApartmentDsGateway {

    private final JpaApartmentRepository repository;

    // Existence checks
    @Override
    public boolean existsByBuildingIdAndAptNumber(String buildingId, String aptNumber) {
        return repository.existsByBuildingDataMapper_IdAndAptNumber(buildingId, aptNumber);
    }

    @Override
    public List<Apartment> findByBuildingIdAndAptNumberIn(String buildingId, List<String> aptNumber) {
        return repository.findByBuildingDataMapper_IdAndAptNumberIn(buildingId, aptNumber).stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Apartment> findByBuildingIdIn(List<String> buildingId) {
        return repository.findByBuildingDataMapper_IdIn(buildingId).stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsDuplicateAptNumber(String buildingId, String aptNumber, String excludeId) {
        Optional<ApartmentDataMapper> existing = repository.findByIdAndDeletedAtIsNull(excludeId);
        if (existing.isEmpty()) {
            // If excluding apartment doesn't exist, just check normal duplicate
            return existsByBuildingIdAndAptNumber(buildingId, aptNumber);
        }

        // Check if apt_number exists in same building but different apartment
        boolean exists = repository.existsByBuildingDataMapper_IdAndAptNumber(buildingId, aptNumber);
        if (!exists) return false;

        // If exists, check if it's the same apartment (by ID)
        ApartmentDataMapper existingApt = existing.get();
        List<ApartmentDataMapper> allWithNumber = repository.findByBuildingDataMapper_IdAndComplexIdAndDeletedAtIsNull(
                buildingId, existingApt.getComplexId()
        );

        return allWithNumber.stream()
                .anyMatch(a -> a.getAptNumber().equals(aptNumber) && !a.getId().equals(excludeId));
    }

    // Find by ID
    @Override
    public Optional<Apartment> findById(String id) {
        return repository.findByIdAndDeletedAtIsNull(id).map(this::mapToEntity);
    }

    // Find by building
    @Override
    public PageResponse<Apartment> findByBuildingId(String buildingId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "createdAt");
        Page<ApartmentDataMapper> page = repository.findByBuildingDataMapper_IdAndDeletedAtIsNull(buildingId, pageable);

        return new PageResponse<>(
                page.getContent().stream().map(this::mapToEntity).collect(Collectors.toList()),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    // Find by status with filters
    @Override
    public PageResponse<Apartment> findByStatus(int status, FilterApartmentInpRequest filter) {
        Sort.Direction direction = filter.getOrder() != null && filter.getOrder().equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(
                filter.getPageNumber(),
                filter.getPageSize(),
                direction, "createdAt"
        );

        Specification<ApartmentDataMapper> spec = ApartmentSpecification.filter(
                status,
                filter.getKeyword(),
                filter.getTimeRequestStart(),
                filter.getTimeRequestEnd()
        );

        Page<ApartmentDataMapper> page = repository.findAll(spec, pageable);

        return new PageResponse<>(
                page.getContent().stream().map(this::mapToEntity).collect(Collectors.toList()),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    // Find by complex
    @Override
    public PageResponse<Apartment> findByComplexId(String complexId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "createdAt");
        Page<ApartmentDataMapper> page = repository.findByComplexIdAndDeletedAtIsNull(complexId, pageable);

        return new PageResponse<>(
                page.getContent().stream().map(this::mapToEntity).collect(Collectors.toList()),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    // Find batch
    @Override
    public List<Apartment> findAllByStatusAndIds(int status, List<String> ids) {
        return repository.findAllByStatusAndIdInAndDeletedAtIsNull(status, ids).stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }

    // Save single
    @Override
    public Apartment save(Apartment apartment) {
        ApartmentDataMapper mapper = mapToDataMapper(apartment);
        ApartmentDataMapper saved = repository.save(mapper);
        return mapToEntity(saved);
    }

    @Override
    public void importExcel(List<Apartment> apartments) {
        List<ApartmentDataMapper> apartmentDataMapperList = apartments.stream()
                .map(this::mapToDataMapper)
                .toList();

        repository.saveAll(apartmentDataMapperList);
    }

    // Update
    @Override
    public Apartment update(String id, Apartment apartment) {
        ApartmentDataMapper mapper = mapToDataMapper(apartment);
        mapper.setId(id);
        mapper.setUpdatedAt(LocalDateTime.now());
        ApartmentDataMapper updated = repository.save(mapper);
        return mapToEntity(updated);
    }

    // Batch update status
    @Override
    public void updateStatusBatch(List<String> ids, int newStatus) {
        List<ApartmentDataMapper> apartments = repository.findAllById(ids);
        apartments.forEach(apt -> {
            apt.setStatus(newStatus);
            apt.setUpdatedAt(LocalDateTime.now());
        });
        repository.saveAll(apartments);
    }

    // Soft delete
    @Override
    public void softDelete(String id) {
        Optional<ApartmentDataMapper> existing = repository.findById(id);
        if (existing.isPresent()) {
            ApartmentDataMapper apt = existing.get();
            apt.setDeletedAt(LocalDateTime.now());
            apt.setUpdatedAt(LocalDateTime.now());
            repository.save(apt);
        }
    }

    // Batch soft delete
    @Override
    public void softDeleteBatch(List<String> ids) {
        List<ApartmentDataMapper> apartments = repository.findAllById(ids);
        apartments.forEach(apt -> {
            apt.setDeletedAt(LocalDateTime.now());
            apt.setUpdatedAt(LocalDateTime.now());
        });
        repository.saveAll(apartments);
    }


    // Mapping methods
    private Apartment mapToEntity(ApartmentDataMapper mapper) {
        return new Apartment(
                mapper.getId(),
                mapper.getBuildingDataMapper().getId(),
                mapper.getComplexId(),
                mapper.getFloor(),
                mapper.getAptNumber(),
                mapper.getGrossArea(),
                mapper.getCarpetArea(),
                mapper.getCoefficient(),
                mapper.getAptType(),
                mapper.getDescription(),
                mapper.getStatus(),
                mapper.getCreatedAt(),
                mapper.getUpdatedAt(),
                mapper.getDeletedAt()
        );
    }

    private ApartmentDataMapper mapToDataMapper(Apartment apartment) {
        return new ApartmentDataMapper(
                apartment.getId(),
                BuildingDataMapper.builder().id(apartment.getBuildingId()).build(),
                apartment.getComplexId(),
                apartment.getFloor(),
                apartment.getAptNumber(),
                apartment.getGrossArea(),
                apartment.getCarpetArea(),
                apartment.getCoefficient(),
                apartment.getAptType(),
                apartment.getDescription(),
                apartment.getStatus(),
                apartment.getCreatedAt(),
                apartment.getUpdatedAt(),
                apartment.getDeletedAt()
        );
    }
}
