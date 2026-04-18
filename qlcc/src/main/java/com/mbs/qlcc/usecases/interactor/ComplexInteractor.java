package com.mbs.qlcc.usecases.interactor;

import com.mbs.qlcc.entities.Complex.Complex;
import com.mbs.qlcc.entities.Complex.IComplexFactory;
import com.mbs.qlcc.usecases.exception.AppException;
import com.mbs.qlcc.usecases.input.IComplexInputBoundary;
import com.mbs.qlcc.usecases.output.Complex.IComplexDsGateway;
import com.mbs.qlcc.usecases.output.Email.IEmailDsGateway;
import com.mbs.qlcc.usecases.output.User.ITokenDsGateway;
import com.mbs.qlcc.usecases.output.User.IUserDsGateway;
import com.mbs.qlcc.usecases.request.Complex.ApproveRejectComplexInpRequest;
import com.mbs.qlcc.usecases.request.Complex.CreateComplexInpRequest;
import com.mbs.qlcc.usecases.request.Complex.FilterComplexInpRequest;
import com.mbs.qlcc.usecases.request.User.UserInpRequest;
import com.mbs.qlcc.usecases.response.Complex.ComplexResponse;
import com.mbs.qlcc.usecases.response.PageResponse;
import com.mbs.qlcc.utils.Constant;
import com.mbs.qlcc.utils.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Complex Interactor - Implements Complex Use Cases
 * Contains all business logic for Complex operations
 */
public class ComplexInteractor implements IComplexInputBoundary {

    IComplexDsGateway complexGateway;
    IComplexFactory complexFactory;
    IEmailDsGateway emailDsGateway;
    IUserDsGateway userDsGateway;
    ITokenDsGateway tokenDsGateway;


    public ComplexInteractor(IComplexDsGateway complexGateway, IComplexFactory complexFactory, IEmailDsGateway emailDsGateway, IUserDsGateway userDsGateway, ITokenDsGateway tokenDsGateway) {
        this.complexGateway = complexGateway;
        this.complexFactory = complexFactory;
        this.emailDsGateway = emailDsGateway;
        this.userDsGateway = userDsGateway;
        this.tokenDsGateway = tokenDsGateway;
    }

    @Override
    public ComplexResponse create(CreateComplexInpRequest request) {
        // Validate: Complex name must not exist
        if (complexGateway.existsByComplexName(request.getComplexName())) {
            throw new AppException(ErrorCode.COMPLEX_NAME_EXISTED);
        }

        // Validate: Address must not exist
        if (complexGateway.existsByAddress(request.getAddress())) {
            throw new AppException(ErrorCode.COMPLEX_ADDRESS_EXISTED);
        }

        // Validate: Phone contact must not exist
        if (complexGateway.existsByPhoneContact(request.getPhoneContact())) {
            throw new AppException(ErrorCode.COMPLEX_PHONE_EXISTED);
        }

        // Create Complex entity using factory
        Complex complex = complexFactory.create(
                request.getComplexName(),
                request.getAddress(),
                request.getTotalBuilding(),
                request.getTotalApartment(),
                request.getNameContact(),
                request.getPhoneContact(),
                request.getEmailContact(),
                request.getDescription(),
                request.getFinancialModel()
        );

        // Save to database
        Complex saved = complexGateway.save(complex);

        // Return response
        return mapToResponse(saved);
    }

    @Override
    public ComplexResponse findById(String id) {
        Optional<Complex> complex = complexGateway.findById(id);
        if (complex.isEmpty()) {
            throw new AppException(ErrorCode.COMPLEX_NOT_FOUND);
        }
        return mapToResponse(complex.get());
    }

    @Override
    public PageResponse<ComplexResponse> filterByStatus(int status, FilterComplexInpRequest request) {
        PageResponse<Complex> result = complexGateway.findByStatus(status, request);

        return new PageResponse<ComplexResponse>(
                result.getData().stream().map(this::mapToResponse).toList(),
                result.getPage(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    @Override
    public List<ComplexResponse> approveComplex(ApproveRejectComplexInpRequest request) {
        if (request.getIds() == null || request.getIds().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        List<Complex> approved = complexGateway.updateMultipleStatusToApproved(request.getIds());
        try {
            for (Complex c : approved) {
                //tao user
                String passwordRaw = "1"; //userDsGateway.generatePassword();
                UserInpRequest userInpRequest = new UserInpRequest(c.getPhoneContact(), tokenDsGateway.hash(passwordRaw), c.getId(), "", "");
                userDsGateway.store(userInpRequest);
                //gan role cho acc
                //gui mail
                emailDsGateway.sendMail(c.getEmailContact(), Constant.SUBJECT.getValue(), Constant.SYSTEM_NAME.getValue(),
                        c.getNameContact(), c.getPhoneContact(), passwordRaw);
            }
        } catch (Exception e) {
            throw new AppException(ErrorCode.NOT_CREATED);
        }

        return approved.stream().map(this::mapToResponse).toList();
    }

    @Override
    public void rejectComplex(ApproveRejectComplexInpRequest request) {
        if (request.getIds() == null || request.getIds().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }
        // Update all complexes to rejected status
        complexGateway.updateMultipleStatusToRejected(request.getIds());
    }

    /**
     * Map Complex entity to response DTO
     */
    private ComplexResponse mapToResponse(Complex complex) {
        return new ComplexResponse(
                complex.getId(),
                complex.getComplexName(),
                complex.getAddress(),
                complex.getTotalBuilding(),
                complex.getTotalApartment(),
                complex.getNameContact(),
                complex.getPhoneContact(),
                complex.getEmailContact(),
                complex.getDescription(),
                complex.getFinancialModel());

    }
}

