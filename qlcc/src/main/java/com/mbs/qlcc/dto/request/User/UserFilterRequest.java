package com.mbs.qlcc.dto.request.User;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFilterRequest {
    private String buildingId;
    private int floor;
    String aptNumber;
    String relationship;
}
