package com.mbs.qlcc.service.impl;

import com.mbs.qlcc.domain.Complex;
import com.mbs.qlcc.domain.OrgUser;
import com.mbs.qlcc.domain.Staff;
import com.mbs.qlcc.domain.User;
import com.mbs.qlcc.dto.request.Staff.StaffRequest;
import com.mbs.qlcc.dto.response.Staff.StaffResponse;
import com.mbs.qlcc.exception.AppException;
import com.mbs.qlcc.mapper.Staff.StaffMapper;
import com.mbs.qlcc.repository.Complex.IComplexRepository;
import com.mbs.qlcc.repository.Organization.IOrgUserRepository;
import com.mbs.qlcc.repository.Staff.IStaffRepository;
import com.mbs.qlcc.repository.User.IUserRepository;
import com.mbs.qlcc.service.IEmailService;
import com.mbs.qlcc.service.IStaffService;
import com.mbs.qlcc.utils.Constant;
import com.mbs.qlcc.utils.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StaffServiceImpl implements IStaffService {

    IStaffRepository staffRepository;
    IUserRepository userRepository;
    IOrgUserRepository orgUserRepository;
    IComplexRepository complexRepository;
    IEmailService emailService;
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public StaffResponse create(StaffRequest request, String complexId) {
        // 1. Kiểm tra Complex tồn tại
        Complex complex = complexRepository.findById(complexId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPLEX_NOT_FOUND));

        // 2. Kiểm tra email, phone trùng trong chung cư
        List<String> existingEmails = staffRepository.findEmailsByComplexId(complexId, List.of(request.getEmail()));
        List<String> existingPhones = staffRepository.findPhoneNumbersByComplexId(complexId, List.of(request.getPhoneNumber()));
        if (!existingEmails.isEmpty() || !existingPhones.isEmpty()) {
            throw new AppException(ErrorCode.STAFF_EXISTED);
        }

        // 3. Kiểm tra username (phone) chưa có user trong hệ thống
        List<User> existingUsers = userRepository.getUserIdByUsername(Set.of(request.getPhoneNumber()), complexId);
        if (!existingUsers.isEmpty()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        // 4. Tạo Staff
        Staff staff = Staff.builder()
                .complexId(complexId)
                .fullname(request.getFullname())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .status(0)
                .build();
        Staff savedStaff = staffRepository.save(staff);

        // 5. Tạo User (username = phoneNumber, password = "1" tạm thời)
        String passwordRaw = "1";
        User user = User.builder()
                .username(savedStaff.getPhoneNumber())
                .passwordHash(passwordEncoder.encode(passwordRaw))
                .complexId(complexId)
                .staffId(savedStaff.getId())
                .build();
        User savedUser = userRepository.save(user);

        // 6. Gán Role cho User thông qua OrgUser
        OrgUser orgUser = OrgUser.builder()
                .userId(savedUser.getId())
                .orgId(request.getOrgId())
                .roleId(request.getRoleId())
                .build();
        OrgUser savedOrgUser = orgUserRepository.save(orgUser);

        // 7. Gửi email thông tin tài khoản (async)
        emailService.sendMail(
                savedStaff.getEmail(),
                Constant.SUBJECT.getValue(),
                "Đại diện " + complex.getComplexName(),
                savedStaff.getFullname(),
                savedStaff.getPhoneNumber(),
                passwordRaw
        );

        return StaffMapper.toResponse(savedStaff, savedOrgUser.getId(), savedOrgUser.getRoleId());
    }
}
