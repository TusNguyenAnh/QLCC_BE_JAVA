package com.mbs.qlcc.adapters.db.Task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTaskTypeRepository extends JpaRepository<TaskTypeDataMapper, String> {
    Page<TaskTypeDataMapper> findByComplexId(String complexId, Pageable pageable);
}
