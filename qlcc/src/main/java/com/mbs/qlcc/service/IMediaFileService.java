package com.mbs.qlcc.service;

import com.mbs.qlcc.domain.MediaFile;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IMediaFileService {
    void create(List<MultipartFile> files, String ownerType, String ownerId) throws IOException;

    Map<String, List<String>> findByOwnerId(List<String> ownerId);

    void deleteByOwner(String ownerType, String ownerId);

    List<MediaFile> findByOwner(String ownerType, String ownerId);

    Optional<MediaFile> findFirstByOwner(String ownerType, String ownerId);

    Map<String, MediaFile> findMapByOwnerIds(String ownerType, List<String> ownerIds);

}
