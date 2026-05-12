package com.mbs.qlcc.usecases.output.User;

import com.mbs.qlcc.entities.User.User;
import com.mbs.qlcc.usecases.request.User.UserInpRequest;

import java.util.List;

public interface IUserDsGateway {
    User findById(String id);
    User findByUsernameAndComplexId(String username, String complexId);
    void storeList(List<UserInpRequest> userInpRequests);
    User store(UserInpRequest userInpRequests);
    String generatePassword();

//    UserResponse  update(String id, array String data);
//    void  getBuildingIdsManage(String userId);
//    void  findByOrgId(String orgId,String table);
//    void  findByCondition(String field, String listItem, String complexId);
//    void  findByBuildingId(array String filters, String complexId);
}
