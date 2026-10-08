package com.mbs.qlcc.service.impl;

import com.mbs.qlcc.domain.Complex;
import com.mbs.qlcc.domain.OrgUser;
import com.mbs.qlcc.domain.User;
import com.mbs.qlcc.dto.request.Complex.ApproveRejectComplexRequest;
import com.mbs.qlcc.dto.request.Complex.CreateComplexRequest;
import com.mbs.qlcc.dto.request.Complex.FilterComplexRequest;
import com.mbs.qlcc.dto.request.User.UserRequest;
import com.mbs.qlcc.dto.response.Complex.ComplexResponse;
import com.mbs.qlcc.dto.response.PageResponse;
import com.mbs.qlcc.exception.AppException;
import com.mbs.qlcc.mapper.Complex.ComplexMapper;
import com.mbs.qlcc.mapper.User.UserMapper;
import com.mbs.qlcc.repository.Complex.IComplexRepository;
import com.mbs.qlcc.repository.Organization.IOrgUserRepository;
import com.mbs.qlcc.repository.Role.IRoleRepository;
import com.mbs.qlcc.repository.Specification.ComplexSpecification;
import com.mbs.qlcc.repository.User.IUserRepository;
import com.mbs.qlcc.service.IComplexService;
import com.mbs.qlcc.service.IEmailService;
import com.mbs.qlcc.service.IMediaFileService;
import com.mbs.qlcc.utils.Constant;
import com.mbs.qlcc.utils.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComplexServiceImpl implements IComplexService {
    IComplexRepository complexRepository;
    IRoleRepository roleRepository;
    IUserRepository userRepository;
    IOrgUserRepository orgUserRepository;
    IMediaFileService mediaFileService;
    IEmailService emailService;
    final PasswordEncoder encoder;

    @Transactional
    public ComplexResponse create(CreateComplexRequest request) throws IOException {
        // Validate: Complex name must not exist
        if (complexRepository.existsByComplexName(request.getComplexName())) {
            throw new AppException(ErrorCode.COMPLEX_NAME_EXISTED);
        }
        // Validate: Address must not exist
        if (complexRepository.existsByAddress(request.getAddress())) {
            throw new AppException(ErrorCode.COMPLEX_ADDRESS_EXISTED);
        }

        // Validate: Phone contact must not exist
        if (complexRepository.existsByPhoneContact(request.getPhoneContact())) {
            throw new AppException(ErrorCode.COMPLEX_PHONE_EXISTED);
        }

        // Save to database
        Complex saved = complexRepository.save(ComplexMapper.toEntity(request));

        ComplexResponse complexResponse = ComplexMapper.toResponse(saved);
        mediaFileService.create(request.getFiles(), "complex", complexResponse.getId());
        return complexResponse;
    }
    // lam tiep cac ham duoi
    public ComplexResponse findById(String id) {
        Optional<Complex> complex = complexRepository.findById(id);
        if (complex.isEmpty()) {
            throw new AppException(ErrorCode.COMPLEX_NOT_FOUND);
        }
        return ComplexMapper.toResponse(complex.get());
    }

    public PageResponse<ComplexResponse> filterByStatus(int status, FilterComplexRequest request) {
        Sort.Direction direction = request.getOrder() != null && request.getOrder().equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(
                request.getPageNumber() - 1,
                request.getPageSize(),
                direction,
                "createdAt"
        );

        Specification<Complex> spec =
                ComplexSpecification.filter(
                        status,
                        request.getKeyword(),
                        request.getTimeRequestStart(),
                        request.getTimeRequestEnd()
                );

        Page<Complex> page = complexRepository.findAll(spec, pageable);

        return new PageResponse<ComplexResponse>(
                page.getContent().stream().map(ComplexMapper::toResponse).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    @Transactional
    public List<ComplexResponse> approveComplex(ApproveRejectComplexRequest request) {
        if (request.getIds() == null || request.getIds().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        List<Complex> complexes = complexRepository.findAllByStatusAndIdIn(0, request.getIds());
        complexes.forEach(c -> {
            c.setStatus(1);
            c.setUpdatedAt(Instant.now());
        });
        List<Complex> approved = complexRepository.saveAll(complexes);

        String roleAdminId = roleRepository.findByRoleNameAndComplexId(Constant.ROLE_ADMIN.getValue(), "").getId();

        try {
            for (Complex c : approved) {
                //tao user
                String passwordRaw = "1"; //userDsGateway.generatePassword();
                User userInp = UserMapper.toEntity(new UserRequest(c.getPhoneContact(), passwordRaw, "", c.getNameContact(), c.getEmailContact(), c.getId(), "", ""));
                userInp.setPasswordHash(encoder.encode(passwordRaw));
                userInp.setCreatedAt(Instant.now());
                userInp.setUpdatedAt(Instant.now());
                User user = userRepository.save(userInp);

                //gan role cho acc
                OrgUser orgUser = OrgUser.builder()
                        .userId(user.getId())
                        .orgId("")
                        .roleId(roleAdminId)
                        .build();
                orgUserRepository.save(orgUser);

                //gui mail
                emailService.sendMail(c.getEmailContact(), Constant.SUBJECT.getValue(), Constant.SYSTEM_NAME.getValue(),
                        c.getNameContact(), c.getPhoneContact(), passwordRaw);
            }
        } catch (Exception e) {
            throw new AppException(ErrorCode.NOT_CREATED);
        }

        return approved.stream().map(ComplexMapper::toResponse).toList();
    }

    @Transactional
    public void rejectComplex(ApproveRejectComplexRequest request) {
        if (request.getIds() == null || request.getIds().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        List<Complex> complexes = complexRepository.findAllByStatusAndIdIn(0, request.getIds());
        complexes.forEach(c -> {
            c.setStatus(2);
            c.setUpdatedAt(Instant.now());
            c.setDeletedAt(Instant.now());
        });
        complexRepository.saveAll(complexes);
    }
}
