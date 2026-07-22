package com.mbs.qlcc.usecases.interactor;

import com.mbs.qlcc.entities.Complex.Complex;
import com.mbs.qlcc.entities.Organization.OrgUser;
import com.mbs.qlcc.entities.Resident.Resident;
import com.mbs.qlcc.entities.User.User;
import com.mbs.qlcc.usecases.exception.AppException;
import com.mbs.qlcc.usecases.input.IUserInputBoundary;
import com.mbs.qlcc.usecases.output.Complex.IComplexDsGateway;
import com.mbs.qlcc.usecases.output.Email.IEmailDsGateway;
import com.mbs.qlcc.usecases.output.Organization.IOrgUserDsGateway;
import com.mbs.qlcc.usecases.output.Resident.IResidentDsGateway;
import com.mbs.qlcc.usecases.output.User.ITokenDsGateway;
import com.mbs.qlcc.usecases.output.User.IUserDsGateway;
import com.mbs.qlcc.usecases.request.User.UserFilterInpRequest;
import com.mbs.qlcc.usecases.request.User.UserInpRequest;
import com.mbs.qlcc.usecases.response.User.IResUserResponse;
import com.mbs.qlcc.usecases.response.User.IStaffUserResponse;
import com.mbs.qlcc.usecases.response.User.UserResponse;
import com.mbs.qlcc.utils.Constant;
import com.mbs.qlcc.utils.ErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UserInteractor implements IUserInputBoundary {
    IComplexDsGateway complexDsGateway;
    IUserDsGateway userDsGateway;
    IResidentDsGateway residentDsGateway;
    IOrgUserDsGateway orgUserDsGateway;
    IEmailDsGateway emailDsGateway;

    public UserInteractor(IUserDsGateway userDsGateway, IComplexDsGateway complexDsGateway, IResidentDsGateway residentDsGateway, IOrgUserDsGateway orgUserDsGateway, IEmailDsGateway emailDsGateway) {
        this.userDsGateway = userDsGateway;
        this.complexDsGateway = complexDsGateway;
        this.residentDsGateway = residentDsGateway;
        this.orgUserDsGateway = orgUserDsGateway;
        this.emailDsGateway = emailDsGateway;
    }

    @Override
    public void create(List<UserInpRequest> request, String complexId) {
        Complex complex = complexDsGateway.findById(complexId).get();
        Set<String> cccdSet = request.stream().map(UserInpRequest::getCccd)
                .filter(cccd -> cccd != null && !cccd.isEmpty())
                .collect(Collectors.toSet());

        List<Resident> existingCccd = residentDsGateway.findCccdsByComplexId(complexId, cccdSet);

        if (cccdSet.size() != existingCccd.size()) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }

        Set<String> phoneSet = request.stream().map(UserInpRequest::getPhoneNumber)
                .filter(phone -> phone != null && !phone.isEmpty())
                .collect(Collectors.toSet());

        Map<String, String> existingPhone = userDsGateway.getUserIdByUsername(phoneSet, complexId);

        if (!existingPhone.isEmpty())
            throw new AppException(ErrorCode.USER_EXISTED);

        try {
            //tao user
            for (UserInpRequest u : request) {
                String passwordRaw = "1"; //userDsGateway.generatePassword();
                u.setPassword(passwordRaw);
            }

            //gan role cho acc
            List<User> saveUser = userDsGateway.storeList(request);
            List<OrgUser> orgUsers = new ArrayList<>();
            for (User us : saveUser) {
                orgUsers.add(new OrgUser(us.getId(), "", ""));
            }
            orgUserDsGateway.saveOrgUser(orgUsers);

            //gui mail (sau khi set password raw)
            for (UserInpRequest u : request) {
                emailDsGateway.sendMail(u.getEmail(), Constant.SUBJECT.getValue(), "Đại diện " + complex.getComplexName(),
                        u.getFullname(), u.getPhoneNumber(), u.getPassword());
            }
        } catch (Exception e) {
            throw new AppException(ErrorCode.NOT_CREATED);
        }
    }

    @Override
    public List<IStaffUserResponse> findStaffByOrgId(String orgId) {
        return userDsGateway.findStaffByOrgId(orgId);
    }

    @Override
    public List<IResUserResponse> findResByOrgId(String orgId) {
        return userDsGateway.findResByOrgId(orgId);
    }

    @Override
    public List<IResUserResponse> filterUser(UserFilterInpRequest request, String complexId) {
        return userDsGateway.filterUser(request, complexId);
    }
}
