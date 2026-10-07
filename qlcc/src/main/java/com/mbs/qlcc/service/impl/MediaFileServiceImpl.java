package com.mbs.qlcc.service.impl;

import com.mbs.qlcc.domain.MediaFile;
import com.mbs.qlcc.dto.request.MediaFile.MediaFileRequest;
import com.mbs.qlcc.mapper.MediaFile.MediaFileMapper;
import com.mbs.qlcc.repository.MediaFile.IMediaFileRepository;
import com.mbs.qlcc.service.IMediaFileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MediaFileServiceImpl implements IMediaFileService {

    IMediaFileRepository mediaFileRepository;
    static final String BASE_PATH = "uploads";

    @Override
    public void create(List<MultipartFile> files, String ownerType, String ownerId) throws IOException {
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
