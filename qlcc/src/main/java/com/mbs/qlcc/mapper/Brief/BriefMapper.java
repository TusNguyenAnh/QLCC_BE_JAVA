package com.mbs.qlcc.mapper.Brief;

import com.mbs.qlcc.domain.Brief;
import com.mbs.qlcc.domain.MediaFile;
import com.mbs.qlcc.dto.response.Brief.BriefResponse;
import org.springframework.stereotype.Component;

@Component
public class BriefMapper {

    public BriefResponse toResponse(Brief entity, MediaFile mediaFile) {
        if (entity == null) return null;

        String fileUrl = null;
        String fileName = null;
        Long fileSize = null;
        String mimeType = null;

        if (mediaFile != null) {
            fileUrl = mediaFile.getFileUrl() != null ? mediaFile.getFileUrl().replace("uploads", "/media") : null;
            fileName = mediaFile.getFileName();
            fileSize = mediaFile.getSize();
            mimeType = mediaFile.getMimeType();
        }

        return new BriefResponse(
                entity.getId(),
                entity.getComplexId(),
                entity.getBriefName(),
                entity.getType(),
                fileUrl,
                fileName,
                fileSize,
                mimeType,
                entity.isDeleted(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public BriefResponse toResponse(Brief entity) {
        return toResponse(entity, null);
    }
}
