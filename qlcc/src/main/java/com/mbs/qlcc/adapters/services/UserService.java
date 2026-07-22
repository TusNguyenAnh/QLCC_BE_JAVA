package com.mbs.qlcc.adapters.services;

import com.mbs.qlcc.adapters.request.User.UserFilterRequest;
import com.mbs.qlcc.adapters.request.User.UserRequest;
import com.mbs.qlcc.usecases.input.IUserInputBoundary;
import com.mbs.qlcc.usecases.request.User.UserFilterInpRequest;
import com.mbs.qlcc.usecases.request.User.UserInpRequest;
import com.mbs.qlcc.usecases.response.User.IResUserResponse;
import com.mbs.qlcc.usecases.response.User.IStaffUserResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    IUserInputBoundary userInputBoundary;

    public void create(List<UserRequest> req, String complexId) {
        List<UserInpRequest> list = req.stream()
                .map(r -> new UserInpRequest(
                        r.getPhoneNumber(), "", r.getCccd(), r.getFullname(), r.getEmail(), complexId, r.getId(), ""
                ))
                .toList();
        userInputBoundary.create(list, complexId);
    }

    public List<IStaffUserResponse> findStaffByOrgId(String orgId) {
        return userInputBoundary.findStaffByOrgId(orgId);
    }

    public List<IResUserResponse> findResByOrgId(String orgId) {
        return userInputBoundary.findResByOrgId(orgId);
    }

    public List<IResUserResponse> filterUser(UserFilterRequest request, String complexId) {
        UserFilterInpRequest inpRequest = new UserFilterInpRequest(request.getBuildingId(), request.getFloor(), request.getAptNumber(), request.getRelationship());
        return userInputBoundary.filterUser(inpRequest, complexId);
    }
}
