package com.mbs.qlcc.adapters.db.Building;

import com.mbs.qlcc.entities.Building.Building;
import com.mbs.qlcc.entities.Organization.Organization;
import com.mbs.qlcc.usecases.output.Building.IBuildingDsGateway;
import com.mbs.qlcc.usecases.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaBuilding implements IBuildingDsGateway {

    private final JpaBuildingRepository repository;

    @Override
    public Building save(Building building) {
        BuildingDataMapper mapper = mapToData(building);
        BuildingDataMapper saved = repository.save(mapper);
        return mapToDomain(saved);
    }

    @Override
    public Optional<Building> findById(String id) {
        Optional<BuildingDataMapper> optional = repository.findById(id);
        return optional.map(this::mapToDomain);
    }

    @Override
    public List<Building> findByComplexId(String complexId) {
        List<BuildingDataMapper> mappers = repository.findByComplexId(complexId);
        return mappers.stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public PageResponse<Building> findByComplexId(String complexId, int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "createdAt"
        );

        Page<BuildingDataMapper> jpaBuilding = repository.findByComplexId(complexId, pageable);

        return new PageResponse<Building>(
                jpaBuilding.getContent().stream().map(this::mapToDomain).toList(),
                jpaBuilding.getNumber(),
                jpaBuilding.getSize(),
                jpaBuilding.getTotalElements(),
                jpaBuilding.getTotalPages()
        );

    }


    @Override
    public boolean existsByBuildingNameAndComplexId(String buildingName, String complexId) {
        return repository.existsByBuildingNameAndComplexId(buildingName, complexId);
    }

    @Override
    public Map<String, String> findByBuildingName(Set<String> listBuildingName, String complexId) {
        List<BuildingDataMapper> buildingDataMapperList = repository.findByBuildingNameInAndComplexId(listBuildingName, complexId);
        Map<String, String> listBd = new HashMap<>();

        for (BuildingDataMapper bd : buildingDataMapperList
        ) {
            listBd.put(bd.getBuildingName(), bd.getId());
        }

        return listBd;
    }

    @Override
    public List<Building> validateBuildingIds(List<String> ids, String complexId) {
        List<BuildingDataMapper> mappers = repository.findByIdInAndComplexId(ids, complexId);
        return mappers.stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }


    @Override
    public List<Building> findBuildingsWithRatio(List<String> ids, String complexId) {
        List<BuildingDataMapper> mappers = repository.findWithRatioByIdInAndComplexId(ids, complexId);
        return mappers.stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void updateFinancialRatio(String id, Float ratio) {
        repository.updateFinancialRatio(id, ratio);
    }


    @Override
    public void deleteByIds(List<String> ids) {
        repository.softDeleteByIds(ids);
    }

    private Building mapToDomain(BuildingDataMapper mapper) {
        if (mapper == null) return null;
        return new Building(
                mapper.getId(),
                mapper.getComplexId(),
                mapper.getBuildingName(),
                mapper.getStatus(),
                mapper.getFinancialRatio(),
                mapper.getCreatedAt(),
                mapper.getUpdatedAt(),
                mapper.getDeletedAt()
        );
    }

    private BuildingDataMapper mapToData(Building domain) {
        if (domain == null) return null;
        return BuildingDataMapper.builder()
                .id(domain.getId())
                .complexId(domain.getComplexId())
                .buildingName(domain.getBuildingName())
                .status(domain.getStatus())
                .financialRatio(domain.getFinancialRatio())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .deletedAt(domain.getDeletedAt())
                .build();
    }
}
