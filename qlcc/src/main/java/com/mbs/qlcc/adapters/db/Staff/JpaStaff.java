package com.mbs.qlcc.adapters.db.Staff;

import com.mbs.qlcc.entities.Staff.Staff;
import com.mbs.qlcc.usecases.output.Staff.IStaffDsGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaStaff implements IStaffDsGateway {
    private final JpaStaffRepository repository;

    @Override
    public Staff save(Staff staff) {
        StaffDataMapper dataMapper = mapToStaffDataMapper(staff);
        return mapToStaff(repository.save(dataMapper));
    }

    @Override
    public List<String> findEmailsByComplexId(String complexId, List<String> emails) {
        return repository.findIdsByComplexIdAndEmails(complexId, emails);
    }

    @Override
    public List<String> findPhoneNumbersByComplexId(String complexId, List<String> phoneNumbers) {
        return repository.findIdsByComplexIdAndPhoneNumbers(complexId, phoneNumbers);
    }

    private StaffDataMapper mapToStaffDataMapper(Staff staff) {
        return StaffDataMapper.builder()
                .complexId(staff.getComplexId())
                .email(staff.getEmail())
                .fullname(staff.getFullname())
                .phoneNumber(staff.getPhoneNumber())
                .status(staff.getStatus())
                .createdAt(staff.getCreatedAt())
                .updatedAt(staff.getUpdatedAt())
                .deletedAt(staff.getDeletedAt())
                .build();
    }

    private Staff mapToStaff(StaffDataMapper dataMapper) {
        return new Staff(
                dataMapper.getId(),
                dataMapper.getComplexId(),
                dataMapper.getFullname(),
                dataMapper.getEmail(),
                dataMapper.getPhoneNumber(),
                dataMapper.getStatus(),
                dataMapper.getCreatedAt(),
                dataMapper.getUpdatedAt(),
                dataMapper.getDeletedAt()
        );
    }
}
