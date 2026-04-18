package com.mbs.qlcc.usecases.input;

import com.mbs.qlcc.usecases.request.MediaFile.MediaFileInpRequest;
import com.mbs.qlcc.usecases.response.MediaFile.MediaFileResponse;

import java.util.List;
import java.util.Map;

public interface IMediaFileInputBoundary {
    void create(List<MediaFileInpRequest> request);
    Map<String, List<String>> findByOwnerId(String id);
}
