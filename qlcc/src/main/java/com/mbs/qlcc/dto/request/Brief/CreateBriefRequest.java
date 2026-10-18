package com.mbs.qlcc.dto.request.Brief;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record CreateBriefRequest(
        String briefName,
        String fileName,

        @NotBlank(message = "BRIEF_TYPE_NOT_EMPTY")
        String type,

        @NotNull(message = "FILE_REQUIRED")
        MultipartFile file
) {
    public String getEffectiveBriefName() {
        if (briefName != null && !briefName.isBlank()) {
            return briefName.trim();
        }
        if (fileName != null && !fileName.isBlank()) {
            return fileName.trim();
        }
        if (file != null && !file.isEmpty() && file.getOriginalFilename() != null && !file.getOriginalFilename().isBlank()) {
            return file.getOriginalFilename().trim();
        }
        return null;
    }
}
