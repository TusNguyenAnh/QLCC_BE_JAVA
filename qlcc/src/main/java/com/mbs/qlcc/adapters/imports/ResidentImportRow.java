package com.mbs.qlcc.adapters.imports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResidentImportRow {
    private String fullname;
    private int gender;
    private String email;
    private LocalDateTime birthday;
    private String relationship;
    private String phoneNumber;
    private String cccd;
    private List<String> errors;    // Validation errors
}
