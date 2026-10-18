package com.mbs.qlcc.service.impl;

import com.mbs.qlcc.domain.MediaFile;
import com.mbs.qlcc.dto.request.MediaFile.MediaFileRequest;
import com.mbs.qlcc.exception.AppException;
import com.mbs.qlcc.mapper.MediaFile.MediaFileMapper;
import com.mbs.qlcc.repository.MediaFile.IMediaFileRepository;
import com.mbs.qlcc.service.IMediaFileService;
import com.mbs.qlcc.utils.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MediaFileServiceImpl implements IMediaFileService {

    IMediaFileRepository mediaFileRepository;
    static final String BASE_PATH = "uploads";

    @Override
    public void create(List<MultipartFile> files, String ownerType, String ownerId) throws IOException {
        if (files == null || files.isEmpty()) {
            return;
        }

        List<MediaFile> mediaFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileType = Optional.ofNullable(file.getContentType())
                    .map(ct -> ct.split("/")[0])
                    .orElse("default");

            String fileUrl = saveInDisk(file.getOriginalFilename(), ownerType, fileType, file.getBytes());

            MediaFileRequest request = new MediaFileRequest(
                    ownerType,
                    ownerId,
                    fileType,
                    file.getOriginalFilename(),
                    fileUrl,
                    file.getContentType(),
                    file.getSize(),
                    file.getBytes()
            );
            mediaFiles.add(MediaFileMapper.toEntity(request));
        }
        mediaFileRepository.saveAll(mediaFiles);
    }

    @Override
    public Map<String, List<String>> findByOwnerId(List<String> ownerId) {
        Map<String, List<String>> result = new HashMap<>();
        result.put("image", new ArrayList<>());
        result.put("video", new ArrayList<>());
        result.put("application", new ArrayList<>());

        Map<String, List<String>> found = mediaFileRepository.findAllByOwnerId(ownerId)
                .stream()
                .collect(Collectors.groupingBy(
                        MediaFile::getFileType,
                        Collectors.mapping(
                                mf -> mf.getFileUrl().replace(BASE_PATH, "/media"),
                                Collectors.toList()
                        )
                ));

        result.putAll(found);
        return result;
    }

    @Override
    public void deleteByOwner(String ownerType, String ownerId) {
        List<MediaFile> mediaFiles = mediaFileRepository.findByOwnerTypeAndOwnerIdAndDeletedAtIsNull(ownerType, ownerId);
        if (mediaFiles == null || mediaFiles.isEmpty()) {
            return;
        }

        for (MediaFile mediaFile : mediaFiles) {
            deleteFromDisk(mediaFile.getFileUrl());
            mediaFile.setDeletedAt(Instant.now());
        }
        mediaFileRepository.saveAll(mediaFiles);
        log.info("Deleted media files for ownerType: {}, ownerId: {}", ownerType, ownerId);
    }

    @Override
    public List<MediaFile> findByOwner(String ownerType, String ownerId) {
        return mediaFileRepository.findByOwnerTypeAndOwnerIdAndDeletedAtIsNull(ownerType, ownerId);
    }

    @Override
    public Optional<MediaFile> findFirstByOwner(String ownerType, String ownerId) {
        return mediaFileRepository.findFirstByOwnerTypeAndOwnerIdAndDeletedAtIsNull(ownerType, ownerId);
    }

    @Override
    public Map<String, MediaFile> findMapByOwnerIds(String ownerType, List<String> ownerIds) {
        if (ownerIds == null || ownerIds.isEmpty()) {
            return Collections.emptyMap();
        }
        List<MediaFile> files = mediaFileRepository.findByOwnerTypeAndOwnerIdInAndDeletedAtIsNull(ownerType, ownerIds);
        Map<String, MediaFile> map = new HashMap<>();
        for (MediaFile file : files) {
            map.putIfAbsent(file.getOwnerId(), file);
        }
        return map;
    }

    private void deleteFromDisk(String fileUrl) {
        if (fileUrl == null || fileUrl.isBlank()) {
            return;
        }
        try {
            Path basePath = Paths.get(BASE_PATH).toAbsolutePath().normalize();
            Path filePath = Paths.get(fileUrl).toAbsolutePath().normalize();
            if (filePath.startsWith(basePath)) {
                Files.deleteIfExists(filePath);
                log.info("Deleted file from disk: {}", filePath);
            }
        } catch (Exception e) {
            log.error("Failed to delete file from disk: {}", fileUrl, e);
        }
    }

    private String saveInDisk(String fileName, String folder, String type, byte[] data) {
        try {
            String originalName = Paths.get(fileName).getFileName().toString();
            String extension = "";
            int dotIndex = originalName.lastIndexOf(".");
            if (dotIndex > 0) {
                extension = originalName.substring(dotIndex);
            }

            String fileId = UUID.randomUUID().toString();
            Path filePath = Paths.get(BASE_PATH, type, folder, fileId + extension);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, data);

            return filePath.toString().replace("\\", "/");
        } catch (IOException e) {
            throw new RuntimeException("Save file failed", e);
        }
    }
}
