package com.mbs.qlcc.adapters.controllers;

import com.mbs.qlcc.adapters.response.ApiResponse;
import com.mbs.qlcc.adapters.services.MediaFileService;
import com.mbs.qlcc.adapters.services.OrganizationService;
import com.mbs.qlcc.usecases.exception.AppException;
import com.mbs.qlcc.usecases.response.Organization.OrganizationResponse;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.utils.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/image")
@RequiredArgsConstructor
public class MediaFileController {
    private final MediaFileService mediaFileService;

    @GetMapping("/view")
    public ApiResponse<Map<String, List<String>>> viewImage(@RequestParam String ownerId) {
        return ApiResponse.<Map<String, List<String>>>builder()
                .result(mediaFileService.findByOwnerId(ownerId))
                .build();
    }
}
