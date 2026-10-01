package com.mbs.qlcc.controller;

import com.mbs.qlcc.dto.response.ApiResponse;
import com.mbs.qlcc.service.IMediaFileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/image")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MediaFileController {

    IMediaFileService mediaFileService;

    @GetMapping("/view/{id}")
    public ApiResponse<Map<String, List<String>>> viewImage(@PathVariable String id) {
        return ApiResponse.<Map<String, List<String>>>builder()
                .result(mediaFileService.findByOwnerId(id))
                .build();
    }
}
