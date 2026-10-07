package com.mbs.qlcc.dto.request.TaskType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskTypeRequest {
    private String workflowId;
    private String priorityId;
    private String typeName;
    private String description;
}
