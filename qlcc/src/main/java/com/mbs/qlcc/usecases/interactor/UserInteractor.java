package com.mbs.qlcc.usecases.interactor;

import com.mbs.qlcc.usecases.input.IUserInputBoundary;
import com.mbs.qlcc.usecases.output.Complex.IComplexDsGateway;
import com.mbs.qlcc.usecases.output.User.ITokenDsGateway;
import com.mbs.qlcc.usecases.output.User.IUserDsGateway;
import com.mbs.qlcc.usecases.request.User.UserInpRequest;
import com.mbs.qlcc.usecases.response.User.UserResponse;

import java.util.List;

public class UserInteractor implements IUserInputBoundary {
    IUserDsGateway userDsGateway;
    IComplexDsGateway complexDsGateway;
    ITokenDsGateway tokenDsGateway;

    public UserInteractor(IUserDsGateway userDsGateway, IComplexDsGateway complexDsGateway, ITokenDsGateway tokenDsGateway) {
        this.userDsGateway = userDsGateway;
        this.complexDsGateway = complexDsGateway;
        this.tokenDsGateway = tokenDsGateway;
    }

    @Override
    public void create(List<UserInpRequest> request) {
        for (UserInpRequest u : request) {
            u.setPassword(tokenDsGateway.hash("1"));
        }
        userDsGateway.storeList(request);
    }
}
