package com.mbs.qlcc.controller;

import com.mbs.qlcc.dto.response.ApiResponse;
import com.mbs.qlcc.service.IMediaFileService;
import com.mbs.qlcc.utils.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MediaFileController {

    IMediaFileService mediaFileService;

    @PostMapping("/view")
    public ApiResponse<Map<String, List<String>>> viewImage(@RequestBody List<String> ownerId) {
        return ApiResponse.<Map<String, List<String>>>builder()
                .result(mediaFileService.findByOwnerId(ownerId))
                .build();
    }

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Void> upload(@RequestParam("files") List<MultipartFile> files,
                                    @RequestParam("ownerType") String ownerType,
                                    @RequestParam("ownerId") String ownerId) throws IOException {
        mediaFileService.create(files, ownerType, ownerId);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Upload file successfully")
                .build();
    }

    private String getCurrentComplexId() {
        return JwtUtil.getClaim(JwtUtil.getToken()).get("complex_id").toString();
    }
}
