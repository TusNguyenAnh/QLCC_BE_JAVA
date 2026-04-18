package com.mbs.qlcc.adapters.services;

import com.mbs.qlcc.adapters.request.User.UserRequest;
import com.mbs.qlcc.usecases.input.IUserInputBoundary;
import com.mbs.qlcc.usecases.request.User.UserInpRequest;
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

    public void create(List<UserInpRequest> userInpRequest) {
        userInputBoundary.create(userInpRequest);
    }
}
