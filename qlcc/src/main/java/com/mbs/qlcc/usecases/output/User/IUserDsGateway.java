package com.mbs.qlcc.usecases.output.User;

import com.mbs.qlcc.entities.User.User;
import com.mbs.qlcc.usecases.request.User.UserFilterInpRequest;
import com.mbs.qlcc.usecases.request.User.UserInpRequest;
import com.mbs.qlcc.usecases.response.User.IResUserResponse;
import com.mbs.qlcc.usecases.response.User.IStaffUserResponse;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IUserDsGateway {
    User findById(String id);

    User findByUsernameAndComplexId(String username, String complexId);

    List<User> storeList(List<UserInpRequest> userInpRequests);

    User store(UserInpRequest userInpRequests);

    String generatePassword();

    List<String> getBuildingIdsManage(String userId);

    List<IStaffUserResponse> findStaffByOrgId(String orgId);

    List<IResUserResponse> findResByOrgId(String orgId);

    Map<String, String> getUserIdByUsername(Set<String> usernames, String complexId);

    List<IResUserResponse> filterUser(UserFilterInpRequest request, String complexId);
}
