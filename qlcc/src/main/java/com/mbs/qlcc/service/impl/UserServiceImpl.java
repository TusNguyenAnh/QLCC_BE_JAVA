package com.mbs.qlcc.service.impl;

import com.mbs.qlcc.domain.Complex;
import com.mbs.qlcc.domain.OrgUser;
import com.mbs.qlcc.domain.User;
import com.mbs.qlcc.domain.Resident;
import com.mbs.qlcc.dto.request.User.UserFilterRequest;
import com.mbs.qlcc.dto.request.User.UserRequest;
import com.mbs.qlcc.dto.response.User.IResUserResponse;
import com.mbs.qlcc.dto.response.User.IStaffUserResponse;
import com.mbs.qlcc.exception.AppException;
import com.mbs.qlcc.repository.Complex.IComplexRepository;
import com.mbs.qlcc.repository.Organization.IOrgUserRepository;
import com.mbs.qlcc.repository.User.IUserRepository;
import com.mbs.qlcc.repository.Resident.IResidentRepository;
import com.mbs.qlcc.service.IEmailService;
import com.mbs.qlcc.service.IUserService;
import com.mbs.qlcc.utils.Constant;
import com.mbs.qlcc.utils.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements IUserService {
    IUserRepository userRepository;
    IComplexRepository complexRepository;
    IResidentRepository residentRepository;
    IOrgUserRepository orgUserRepository;
    IEmailService emailService;
    PasswordEncoder encoder;

    @Override
    @Transactional
    public void create(List<UserRequest> req, String complexId) {
        Complex complex = complexRepository.findById(complexId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPLEX_NOT_FOUND));

        Set<String> cccdSet = req.stream().map(UserRequest::getCccd)
                .filter(cccd -> cccd != null && !cccd.isEmpty())
                .collect(Collectors.toSet());

        List<Resident> existingCccd = residentRepository.findByComplexIdAndCccdIn(complexId, cccdSet);

        if (cccdSet.size() != existingCccd.size()) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }

        Set<String> phoneSet = req.stream().map(UserRequest::getPhoneNumber)
                .filter(phone -> phone != null && !phone.isEmpty())
                .collect(Collectors.toSet());

        List<User> existingUsers = userRepository.getUserIdByUsername(phoneSet, complexId);

        if (!existingUsers.isEmpty())
            throw new AppException(ErrorCode.USER_EXISTED);

        try {
            List<User> usersToSave = new ArrayList<>();
            // tao user
            for (UserRequest r : req) {
                String passwordRaw = "1"; // logic tạo password tự động có thể để đây
                
                User user = User.builder()
                        .username(r.getPhoneNumber())
                        .passwordHash(encoder.encode(passwordRaw))
                        .complexId(complexId)
                        .resId(r.getId()) // Giả sử r.getId() là resId
                        .staffId("")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();
                usersToSave.add(user);
            }

            List<User> savedUsers = userRepository.saveAll(usersToSave);

            List<OrgUser> orgUsers = new ArrayList<>();
            for (User us : savedUsers) {
                OrgUser orgUser = OrgUser.builder()
                        .userId(us.getId())
                        .orgId("") // Cần xác định orgId đúng
                        .roleId("") // Cần xác định roleId đúng
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();
                orgUsers.add(orgUser);
            }
            orgUserRepository.saveAll(orgUsers);

            // gui mail 
            for (UserRequest r : req) {
                String passwordRaw = "1";
                emailService.sendMail(r.getEmail(), Constant.SUBJECT.getValue(), "Đại diện " + complex.getComplexName(),
                        r.getFullname(), r.getPhoneNumber(), passwordRaw);
            }
        } catch (Exception e) {
            throw new AppException(ErrorCode.NOT_CREATED);
        }
    }

    @Override
    public List<IStaffUserResponse> findStaffByOrgId(String orgId) {
        return userRepository.findStaffByOrgId(orgId);
    }

    @Override
    public List<IResUserResponse> findResByOrgId(String orgId) {
        return userRepository.findResByOrgId(orgId);
    }

    @Override
    public List<IResUserResponse> filterUser(UserFilterRequest request, String complexId) {
        return userRepository.filterUser(
                complexId, 
                request.getBuildingId() != null && !request.getBuildingId().isEmpty() ? request.getBuildingId() : null,
                request.getFloor() > 0 ? request.getFloor() : null,
                request.getAptNumber() != null && !request.getAptNumber().isEmpty() ? request.getAptNumber() : null,
                request.getRelationship()
        );
    }
}
