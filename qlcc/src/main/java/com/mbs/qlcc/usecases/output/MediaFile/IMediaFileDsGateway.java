package com.mbs.qlcc.usecases.output.MediaFile;

import com.mbs.qlcc.entities.MediaFile.MediaFile;

import java.util.List;
import java.util.Map;

public interface IMediaFileDsGateway {
    void save(List<MediaFile> mediaFiles);
    Map<String, List<String>> findByOwnerId(String ownerId);
    void delete(String fileId);
}
