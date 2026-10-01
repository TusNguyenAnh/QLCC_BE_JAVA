package com.mbs.qlcc.mapper.User;

import com.mbs.qlcc.domain.Complex;
import com.mbs.qlcc.domain.User;
import com.mbs.qlcc.dto.request.Complex.CreateComplexRequest;
import com.mbs.qlcc.dto.request.User.UserRequest;
import com.mbs.qlcc.dto.response.Complex.ComplexResponse;
import com.mbs.qlcc.dto.response.User.UserResponse;

public class UserMapper {
    public UserMapper() {
    }

    public static User toEntity(UserRequest request) {
        return User.builder()
                .username(request.getPhoneNumber())
                .passwordHash(request.getPassword())
                .complexId(request.getComplexId())
                .resId(request.getId())
                .staffId(request.getStaffId())
                .build();
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getUsername(),
                user.getResId(),
                user.getComplexId(),
                user.isDeleted()
        );
    }
}
