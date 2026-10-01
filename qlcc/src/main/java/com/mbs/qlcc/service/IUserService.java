package com.mbs.qlcc.service;

import com.mbs.qlcc.dto.request.User.UserFilterRequest;
import com.mbs.qlcc.dto.request.User.UserRequest;
import com.mbs.qlcc.dto.response.User.IResUserResponse;
import com.mbs.qlcc.dto.response.User.IStaffUserResponse;

import java.util.List;

public interface IUserService {
    void create(List<UserRequest> req, String complexId);
    List<IStaffUserResponse> findStaffByOrgId(String orgId);
    List<IResUserResponse> findResByOrgId(String orgId);
    List<IResUserResponse> filterUser(UserFilterRequest request, String complexId);
}
