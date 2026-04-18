package com.mbs.qlcc.entities.MediaFile;

import java.math.BigInteger;
import java.util.List;

public interface IMediaFileFactory {
    MediaFile create(String ownerType, String ownerId, String fileType, String fileName,
                     String fileUrl, String mimeType, Long size, byte[] data);
}
