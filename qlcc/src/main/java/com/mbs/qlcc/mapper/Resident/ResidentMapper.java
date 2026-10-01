package com.mbs.qlcc.mapper.Resident;

import com.mbs.qlcc.domain.Resident;
import com.mbs.qlcc.dto.request.Resident.CreateResidentRequest;
import com.mbs.qlcc.dto.response.Resident.ResidentResponse;

import java.time.LocalDateTime;

public final class ResidentMapper {
    private ResidentMapper() {}

    public static Resident toEntity(CreateResidentRequest request, String complexId) {
        return Resident.builder()
                .complexId(complexId)
                .fullname(request.getFullname())
                .gender(request.getGender())
                .email(request.getEmail())
                .birthday(request.getBirthday())
                .relationship(request.getRelationship())
                .phoneNumber(request.getPhoneNumber())
                .cccd(request.getCccd())
                .status(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static ResidentResponse toResponse(Resident resident) {
        return new ResidentResponse(
                resident.getId(),
                resident.getComplexId(),
                resident.getFullname(),
                resident.getGender(),
                resident.getEmail(),
                resident.getBirthday(),
                resident.getRelationship(),
                resident.getPhoneNumber(),
                resident.getCccd(),
                resident.getStatus(),
                resident.getCreatedAt(),
                resident.getUpdatedAt()
        );
    }
}
