package com.mbs.qlcc.repository.Task;

import com.mbs.qlcc.domain.TaskType;
import com.mbs.qlcc.dto.response.Task.ITaskTypeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITaskTypeRepository extends JpaRepository<TaskType, String> {
    Optional<TaskType> findByIdAndDeletedAtIsNull(String id);
    @EntityGraph(attributePaths = {"priority"})
    Page<ITaskTypeResponse> findByComplexIdAndDeletedAtIsNull(String complexId, Pageable pageable);
}
