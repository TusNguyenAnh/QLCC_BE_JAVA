package com.mbs.qlcc.usecases.output.Building;

import com.mbs.qlcc.entities.Building.Building;
import com.mbs.qlcc.usecases.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface IBuildingDsGateway {

    // CRUD Operations
    Building save(Building building);

    Optional<Building> findById(String id);

    List<Building> findByComplexId(String complexId);

    PageResponse<Building> findByComplexId(String complexId, int page, int size);

    // Validation Methods
    boolean existsByBuildingNameAndComplexId(String buildingName, String complexId);
    Map<String,String> findByBuildingName(Set<String> listBuildingName, String complexId);

    List<Building> validateBuildingIds(List<String> ids, String complexId);

    // Financial Ratio Operations
    List<Building> findBuildingsWithRatio(List<String> ids, String complexId);

    void updateFinancialRatio(String id, Float ratio);

    // Delete Operations
    void deleteByIds(List<String> ids);
}
