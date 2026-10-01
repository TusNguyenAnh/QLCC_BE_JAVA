package com.mbs.qlcc.dto.request.Resident;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateResidentRequest {
    private String fullname;
    private int gender;
    private String email;
    private LocalDateTime birthday;
    private String relationship;
    private String phoneNumber;
    private String cccd;
}
