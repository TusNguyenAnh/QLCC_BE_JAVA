package com.mbs.qlcc.adapters.request.Staff;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffRequest {
    private String fullname;
    private String email;
    private String phoneNumber;
    private String orgId;
    private String roleId;
}
