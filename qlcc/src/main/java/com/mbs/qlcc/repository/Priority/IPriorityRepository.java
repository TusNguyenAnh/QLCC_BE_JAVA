package com.mbs.qlcc.repository.Priority;

import com.mbs.qlcc.domain.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPriorityRepository extends JpaRepository<Priority, String> {
}
