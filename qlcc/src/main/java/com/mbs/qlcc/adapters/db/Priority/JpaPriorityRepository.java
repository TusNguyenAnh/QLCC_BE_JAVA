package com.mbs.qlcc.adapters.db.Priority;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPriorityRepository extends JpaRepository<PriorityDataMapper, String> {
}
