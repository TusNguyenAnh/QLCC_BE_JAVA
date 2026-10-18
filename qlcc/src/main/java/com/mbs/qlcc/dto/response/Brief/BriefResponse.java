package com.mbs.qlcc.dto.response.Brief;

import java.time.Instant;

public record BriefResponse(
        String id,
        String complexId,
        String briefName,
        String type,
        String fileUrl,
        String fileName,
        Long fileSize,
        String mimeType,
        boolean isDeleted,
        Instant createdAt,
        Instant updatedAt
) {}
