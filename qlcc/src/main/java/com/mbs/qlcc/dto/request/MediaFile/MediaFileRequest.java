package com.mbs.qlcc.dto.request.MediaFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MediaFileRequest {
    private String ownerType;
    private String ownerId;
    private String fileType;
    private String fileName;
    private String fileUrl;
    private String mimeType;
    private Long size;
    private byte[] data;
}
