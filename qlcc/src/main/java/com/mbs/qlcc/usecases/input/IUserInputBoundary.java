package com.mbs.qlcc.usecases.input;

import com.mbs.qlcc.usecases.request.User.UserFilterInpRequest;
import com.mbs.qlcc.usecases.request.User.UserInpRequest;
import com.mbs.qlcc.usecases.response.User.IResUserResponse;
import com.mbs.qlcc.usecases.response.User.IStaffUserResponse;
import com.mbs.qlcc.usecases.response.User.UserResponse;

import java.util.List;

public interface IUserInputBoundary {
    void create(List<UserInpRequest> request,String complexId);
    List<IStaffUserResponse> findStaffByOrgId(String orgId);
    List<IResUserResponse> findResByOrgId(String orgId);
    List<IResUserResponse> filterUser(UserFilterInpRequest request, String complexId);
}
