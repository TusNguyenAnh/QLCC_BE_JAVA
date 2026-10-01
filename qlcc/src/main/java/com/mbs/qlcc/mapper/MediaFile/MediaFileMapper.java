package com.mbs.qlcc.mapper.MediaFile;

import com.mbs.qlcc.domain.Complex;
import com.mbs.qlcc.domain.MediaFile;
import com.mbs.qlcc.dto.request.Complex.CreateComplexRequest;
import com.mbs.qlcc.dto.request.MediaFile.MediaFileRequest;

public class MediaFileMapper {
    public MediaFileMapper() {
    }

    public static MediaFile toEntity(MediaFileRequest mediaFile) {
        if (mediaFile == null) return null;
        return MediaFile.builder()
                .ownerType(mediaFile.getOwnerType())
                .ownerId(mediaFile.getOwnerId())
                .fileType(mediaFile.getFileType())
                .fileName(mediaFile.getFileName())
                .fileUrl(mediaFile.getFileUrl())
                .mimeType(mediaFile.getMimeType())
                .size(mediaFile.getSize())
                .build();
    }
}
