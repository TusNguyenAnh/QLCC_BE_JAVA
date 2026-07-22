package com.mbs.qlcc.usecases.interactor;

import com.mbs.qlcc.entities.Complex.Complex;
import com.mbs.qlcc.entities.Organization.OrgUser;
import com.mbs.qlcc.entities.Staff.IStaffFactory;
import com.mbs.qlcc.entities.Staff.Staff;
import com.mbs.qlcc.entities.User.User;
import com.mbs.qlcc.usecases.exception.AppException;
import com.mbs.qlcc.usecases.input.IStaffInputBoundary;
import com.mbs.qlcc.usecases.output.Complex.IComplexDsGateway;
import com.mbs.qlcc.usecases.output.Email.IEmailDsGateway;
import com.mbs.qlcc.usecases.output.Organization.IOrgUserDsGateway;
import com.mbs.qlcc.usecases.output.Staff.IStaffDsGateway;
import com.mbs.qlcc.usecases.output.User.IUserDsGateway;
import com.mbs.qlcc.usecases.request.Staff.StaffInpRequest;
import com.mbs.qlcc.usecases.request.User.UserInpRequest;
import com.mbs.qlcc.usecases.response.Staff.StaffResponse;
import com.mbs.qlcc.utils.Constant;
import com.mbs.qlcc.utils.ErrorCode;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class StaffInteractor implements IStaffInputBoundary {
    private IStaffDsGateway staffDsGateway;
    private IUserDsGateway userDsGateway;
    private IOrgUserDsGateway orgUserDsGateway;
    private IComplexDsGateway complexDsGateway;
    private IEmailDsGateway emailDsGateway;
    private IStaffFactory staffFactory;

    public StaffInteractor(IStaffDsGateway staffDsGateway, IUserDsGateway userDsGateway, IOrgUserDsGateway orgUserDsGateway, IComplexDsGateway complexDsGateway, IEmailDsGateway emailDsGateway, IStaffFactory staffFactory) {
        this.staffDsGateway = staffDsGateway;
        this.userDsGateway = userDsGateway;
        this.orgUserDsGateway = orgUserDsGateway;
        this.complexDsGateway = complexDsGateway;
        this.emailDsGateway = emailDsGateway;
        this.staffFactory = staffFactory;
    }

    @Override
    public StaffResponse create(StaffInpRequest request) {
        Complex complex = complexDsGateway.findById(request.getComplexId()).get();
        String complexId = request.getComplexId();
        List<String> existingEmails = staffDsGateway.findEmailsByComplexId(complexId, List.of(request.getEmail()));
        List<String> existingPhones = staffDsGateway.findPhoneNumbersByComplexId(complexId, List.of(request.getPhoneNumber()));


        if (!existingPhones.isEmpty() || !existingEmails.isEmpty())
            throw new AppException(ErrorCode.STAFF_EXISTED);

        Map<String, String> existingPhone = userDsGateway.getUserIdByUsername(Set.of(request.getPhoneNumber()), complexId);

        if (!existingPhone.isEmpty())
            throw new AppException(ErrorCode.USER_EXISTED);

        try {
            Staff staff = staffFactory.create(request.getComplexId(), request.getFullname(), request.getEmail(), request.getPhoneNumber(), 0);
            Staff saveStaff = staffDsGateway.save(staff);

            //tao user
            String passwordRaw = "1"; //userDsGateway.generatePassword();
            UserInpRequest user = new UserInpRequest(saveStaff.getPhoneNumber(), passwordRaw, saveStaff.getComplexId(), null, saveStaff.getId());
            User saveUser = userDsGateway.store(user);

            //gan role cho acc
            OrgUser orgUser = new OrgUser(saveUser.getId(), request.getOrgId(), request.getRoleId());
            orgUserDsGateway.saveOrgUser(List.of(orgUser));

            //gui mail (sau khi set password raw)
            emailDsGateway.sendMail(saveStaff.getEmail(), Constant.SUBJECT.getValue(), "Đại diện " + complex.getComplexName(),
                    saveStaff.getFullname(), saveStaff.getPhoneNumber(), passwordRaw);

            return mapToStaffResponse(saveStaff, orgUser);
        } catch (Exception e) {
            throw new AppException(ErrorCode.NOT_CREATED);
        }
    }

    private StaffResponse mapToStaffResponse(Staff staff, OrgUser orgUser) {
        return new StaffResponse(staff.getId(), orgUser.getId(), staff.getFullname(), staff.getEmail(), staff.getPhoneNumber(), orgUser.getRole_id());
    }
}
