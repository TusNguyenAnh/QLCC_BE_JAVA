package com.mbs.qlcc.adapters.db.MediaFile;

import com.mbs.qlcc.adapters.db.Building.BuildingDataMapper;
import com.mbs.qlcc.entities.Building.Building;
import com.mbs.qlcc.entities.MediaFile.MediaFile;
import com.mbs.qlcc.usecases.output.MediaFile.IMediaFileDsGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaMediaFile implements IMediaFileDsGateway {
    private final JpaMediaFileRepository repository;
    private final String basePath = "uploads";

    @Override
    public void save(List<MediaFile> mediaFiles) {
        List<MediaFileDataMapper> mediaFileList = new ArrayList<>();
        for (MediaFile mediaFile : mediaFiles) {
            MediaFileDataMapper mediaFileDataMapper = mapToData(mediaFile);
            String fileUrl = saveInDisk(mediaFile.getFileName(), mediaFile.getOwnerType(), mediaFile.getFileType(), mediaFile.getData());
            mediaFileDataMapper.setFileUrl(fileUrl);
            mediaFileList.add(mediaFileDataMapper);
        }
        repository.saveAll(mediaFileList);
    }

    @Override
    public Map<String, List<String>> findByOwnerId(String ownerId) {
        Map<String, List<String>> defaultMap = new HashMap<>();
        defaultMap.put("image", new ArrayList<>());
        defaultMap.put("video", new ArrayList<>());
        defaultMap.put("application", new ArrayList<>());

        Map<String, List<String>> result = repository.findAllByOwnerId(ownerId)
                .stream()
                .collect(Collectors.groupingBy(
                        MediaFileDataMapper::getFileType,
                        Collectors.mapping(url -> url.getFileUrl().replace(basePath,"/media"), Collectors.toList())
                ));

        defaultMap.putAll(result);
        return defaultMap;
    }

    @Override
    public void delete(String fileId) {

    }

    private MediaFileDataMapper mapToData(MediaFile mediaFile) {
        if (mediaFile == null) return null;
        return MediaFileDataMapper.builder()
                .ownerType(mediaFile.getOwnerType())
                .ownerId(mediaFile.getOwnerId())
                .fileType(mediaFile.getFileType())
                .fileName(mediaFile.getFileName())
                .mimeType(mediaFile.getMimeType())
                .size(mediaFile.getSize())
                .createdAt(mediaFile.getCreatedAt())
                .updatedAt(mediaFile.getUpdatedAt())
                .deletedAt(mediaFile.getDeletedAt())
                .build();
    }

    private MediaFile mapToDomain(MediaFileDataMapper mapper) {
        if (mapper == null) return null;
        return new MediaFile(
                mapper.getId(),
                mapper.getOwnerType(),
                mapper.getOwnerId(),
                mapper.getFileType(),
                mapper.getFileName(),
                mapper.getFileUrl(),
                mapper.getMimeType(),
                mapper.getSize(),
                mapper.getCreatedAt(),
                mapper.getUpdatedAt(),
                mapper.getDeletedAt()
        );
    }

    private String saveInDisk(String fileName, String folder, String type, byte[] data) {
        try {
            String originalName = Paths.get(fileName)
                    .getFileName()
                    .toString();

            // 3. Lấy extension
            String extension = "";
            int i = originalName.lastIndexOf(".");
            if (i > 0) {
                extension = originalName.substring(i);
            }

            String fileId = UUID.randomUUID().toString();
            String lastFileName = fileId + extension;
            Path filePath = Paths.get(basePath, type, folder, lastFileName);

            Files.createDirectories(filePath.getParent());

            Path storage = Files.write(filePath, data);

            return storage.toString().replace("\\", "/");

        } catch (IOException e) {
            throw new RuntimeException("Save file failed");
        }
    }

}
