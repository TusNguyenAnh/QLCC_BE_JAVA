package com.mbs.qlcc.usecases.interactor;

import com.mbs.qlcc.adapters.db.MediaFile.MediaFileDataMapper;
import com.mbs.qlcc.entities.MediaFile.IMediaFileFactory;
import com.mbs.qlcc.entities.MediaFile.MediaFile;
import com.mbs.qlcc.usecases.input.IMediaFileInputBoundary;
import com.mbs.qlcc.usecases.output.MediaFile.IMediaFileDsGateway;
import com.mbs.qlcc.usecases.request.MediaFile.MediaFileInpRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MediaFileInteractor implements IMediaFileInputBoundary {
    IMediaFileDsGateway mediaFileDsGateway;
    IMediaFileFactory mediaFileFactory;

    public MediaFileInteractor(IMediaFileDsGateway mediaFileDsGateway, IMediaFileFactory mediaFileFactory) {
        this.mediaFileDsGateway = mediaFileDsGateway;
        this.mediaFileFactory = mediaFileFactory;
    }

    @Override
    public void create(List<MediaFileInpRequest> request) {
        List<MediaFile> mediaFiles = new ArrayList<>();
        for (MediaFileInpRequest mediaFile : request) {
            MediaFile file = mediaFileFactory.create(mediaFile.getOwnerType(), mediaFile.getOwnerId(), mediaFile.getFileType(), mediaFile.getFileName(),
                    mediaFile.getFileUrl(), mediaFile.getMimeType(), mediaFile.getSize(), mediaFile.getData());
            mediaFiles.add(file);
        }
        mediaFileDsGateway.save(mediaFiles);
    }

    @Override
    public Map<String, List<String>> findByOwnerId(String id) {
        return mediaFileDsGateway.findByOwnerId(id);
    }
}
