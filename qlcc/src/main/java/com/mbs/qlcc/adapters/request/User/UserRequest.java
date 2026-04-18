package com.mbs.qlcc.adapters.request.User;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    String id;
    String phoneNumber;
    String cccd;
    String fullname;
    String email;
}
