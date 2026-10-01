package com.mbs.qlcc.dto.request.User;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    String phoneNumber;
    String password;
    String cccd;
    String fullname;
    String email;
    String complexId;
    String id;
    String staffId;
}
