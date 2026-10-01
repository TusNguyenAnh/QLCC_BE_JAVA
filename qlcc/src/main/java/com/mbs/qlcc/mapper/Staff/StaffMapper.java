package com.mbs.qlcc.mapper.Staff;

import com.mbs.qlcc.domain.Staff;
import com.mbs.qlcc.dto.response.Staff.StaffResponse;

public class StaffMapper {

    private StaffMapper() {}

    /**
     * Chuyển Staff entity + orgUserId + roleId sang StaffResponse DTO.
     */
    public static StaffResponse toResponse(Staff staff, String orgUserId, String roleId) {
        if (staff == null) return null;
        return new StaffResponse(
                staff.getId(),
                orgUserId,
                staff.getFullname(),
                staff.getEmail(),
                staff.getPhoneNumber(),
                roleId
        );
    }
}
