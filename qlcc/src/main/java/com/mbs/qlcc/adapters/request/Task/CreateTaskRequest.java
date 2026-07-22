package com.mbs.qlcc.adapters.request.Task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTaskRequest {
    private String tasktypeId;
    private List<String> buildingId;
    private String taskName;
    private String description;
    private String category;
    List<MultipartFile> files;
}
