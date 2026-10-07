package com.mbs.qlcc.mapper.Priority;

import com.mbs.qlcc.domain.Priority;
import com.mbs.qlcc.dto.response.Priority.PriorityResponse;
import org.springframework.stereotype.Component;

@Component
public class PriorityMapper {

    public static PriorityResponse toResponse(Priority priority) {
        if (priority == null) {
            return null;
        }

        return PriorityResponse.builder()
                .id(priority.getId())
                .priorityName(priority.getPriorityName())
                .description(priority.getDescription())
                .weight(priority.getWeight())
                .build();
    }

    public static Priority toEntity(String priorityName, String description, Integer weight) {
        return Priority.builder()
                .priorityName(priorityName)
                .description(description)
                .weight(weight != null ? weight : 1)
                .isDeleted(false)
                .build();
    }
}
