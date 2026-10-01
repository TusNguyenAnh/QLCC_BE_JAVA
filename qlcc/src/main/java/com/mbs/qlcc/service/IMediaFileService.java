package com.mbs.qlcc.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IMediaFileService {
    void create(List<MultipartFile> files, String ownerType, String ownerId) throws IOException;
    Map<String, List<String>> findByOwnerId(String ownerId);
}
