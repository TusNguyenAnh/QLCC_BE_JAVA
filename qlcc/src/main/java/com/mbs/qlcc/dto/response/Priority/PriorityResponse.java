package com.mbs.qlcc.dto.response.Priority;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriorityResponse {
    private String id;
    private String priorityName;
    private String description;
    private Integer weight;
}
