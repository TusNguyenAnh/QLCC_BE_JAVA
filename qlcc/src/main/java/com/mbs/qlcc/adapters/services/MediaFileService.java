package com.mbs.qlcc.adapters.services;

import com.mbs.qlcc.adapters.db.MediaFile.MediaFileDataMapper;
import com.mbs.qlcc.adapters.request.Complex.CreateComplexRequest;
import com.mbs.qlcc.entities.MediaFile.MediaFile;
import com.mbs.qlcc.usecases.input.IMediaFileInputBoundary;
import com.mbs.qlcc.usecases.request.Complex.CreateComplexInpRequest;
import com.mbs.qlcc.usecases.request.MediaFile.MediaFileInpRequest;
import com.mbs.qlcc.usecases.response.Complex.ComplexResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MediaFileService {
    IMediaFileInputBoundary useCase;

    public void create(List<MultipartFile> file, String ownerType, String ownerId) throws IOException {
        List<MediaFileInpRequest> files = new ArrayList<>();
        for (MultipartFile mediaFile : file) {
            MediaFileInpRequest inpRequest = new MediaFileInpRequest(
                    ownerType,
                    ownerId,
                    Optional.ofNullable(mediaFile.getContentType())
                            .map(m -> m.split("/")[0])
                            .orElse("default"),
                    mediaFile.getOriginalFilename(),
                    "",
                    mediaFile.getContentType(),
                    mediaFile.getSize(),
                    mediaFile.getBytes()
            );
            files.add(inpRequest);
        }
        useCase.create(files);
    }

    public Map<String, List<String>> findByOwnerId(String id) {
        return useCase.findByOwnerId(id);
    }

}
