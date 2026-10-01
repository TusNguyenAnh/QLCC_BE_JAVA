package com.mbs.qlcc.repository.Apartment;

import com.mbs.qlcc.domain.Apartment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface IApartmentRepository extends JpaRepository<Apartment, String>, JpaSpecificationExecutor<Apartment> {

    Optional<Apartment> findByIdAndDeletedAtIsNull(String id);

    boolean existsByBuilding_IdAndAptNumberAndDeletedAtIsNull(String buildingId, String aptNumber);

    @Query("SELECT a FROM Apartment a WHERE a.building.id = :buildingId AND a.deletedAt IS NULL")
    Page<Apartment> findByBuildingId(@Param("buildingId") String buildingId, Pageable pageable);

    @Query("SELECT a FROM Apartment a WHERE a.complexId = :complexId AND a.deletedAt IS NULL")
    Page<Apartment> findByComplexId(@Param("complexId") String complexId, Pageable pageable);

    @Query("SELECT a FROM Apartment a WHERE a.building.id = :buildingId AND a.aptNumber IN :aptNumbers AND a.deletedAt IS NULL")
    List<Apartment> findByBuildingIdAndAptNumberIn(@Param("buildingId") String buildingId, @Param("aptNumbers") Collection<String> aptNumbers);

    List<Apartment> findByBuilding_IdInAndDeletedAtIsNull(Collection<String> buildingIds);
}
