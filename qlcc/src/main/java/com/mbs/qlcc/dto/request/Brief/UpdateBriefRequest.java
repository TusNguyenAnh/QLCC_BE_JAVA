package com.mbs.qlcc.dto.request.Brief;

import jakarta.validation.constraints.NotBlank;

public record UpdateBriefRequest(
        String briefName,
        String fileName,

        @NotBlank(message = "BRIEF_TYPE_NOT_EMPTY")
        String type
) {
    public String getEffectiveBriefName() {
        if (briefName != null && !briefName.isBlank()) {
            return briefName.trim();
        }
        if (fileName != null && !fileName.isBlank()) {
            return fileName.trim();
        }
        return null;
    }
}
