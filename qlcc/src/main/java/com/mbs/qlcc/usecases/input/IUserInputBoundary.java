package com.mbs.qlcc.usecases.input;

import com.mbs.qlcc.usecases.request.User.UserInpRequest;
import com.mbs.qlcc.usecases.response.User.UserResponse;

import java.util.List;

public interface IUserInputBoundary {
    void create(List<UserInpRequest> request);
}
