package com.mbs.qlcc.dto.response.Task;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskTypeResponse {
    private String id;
    private String complexId;
    private String workflowId;
    private String priorityId;
    private String typeName;
    private String description;
    private Integer status;
}
