package com.mbs.qlcc.entities.MediaFile;

import java.math.BigInteger;
import java.util.List;

public class MediaFileFactory implements IMediaFileFactory {
    @Override
    public MediaFile create(String ownerType, String ownerId, String fileType, String fileName, String fileUrl, String mimeType, Long size, byte[] data) {
        return new MediaFile(ownerType, ownerId, fileType, fileName, fileUrl, mimeType, size, data);
    }
}
