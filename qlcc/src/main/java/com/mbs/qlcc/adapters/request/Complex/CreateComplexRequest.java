package com.mbs.qlcc.adapters.request.Complex;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Create Complex Request DTO (From Client)
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateComplexRequest {
    String complexName;
    String address;
    int totalBuilding;
    int totalApartment;
    String nameContact;
    String phoneContact;
    String emailContact;
    String description;
    String financialModel;
    List<MultipartFile> files;
}
