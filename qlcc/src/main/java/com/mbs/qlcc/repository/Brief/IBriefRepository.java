package com.mbs.qlcc.repository.Brief;

import com.mbs.qlcc.domain.Brief;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBriefRepository extends JpaRepository<Brief, String>, JpaSpecificationExecutor<Brief> {

    boolean existsByComplexIdAndTypeAndBriefNameAndIsDeletedFalse(String complexId, String type, String briefName);

    boolean existsByComplexIdAndTypeAndBriefNameAndIdNotAndIsDeletedFalse(String complexId, String type, String briefName, String id);

    Optional<Brief> findByIdAndComplexIdAndIsDeletedFalse(String id, String complexId);

    Optional<Brief> findByIdAndIsDeletedFalse(String id);
}
