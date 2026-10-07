package com.mbs.qlcc.repository.Priority;

import com.mbs.qlcc.domain.Priority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPriorityRepository extends JpaRepository<Priority, String> {
    Page<Priority> findAllByDeletedAtIsNull(Pageable pageable);
}


