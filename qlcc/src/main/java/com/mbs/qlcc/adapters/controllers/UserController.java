package com.mbs.qlcc.adapters.controllers;

import com.mbs.qlcc.adapters.request.User.UserRequest;
import com.mbs.qlcc.adapters.services.UserService;
import com.mbs.qlcc.usecases.request.User.UserInpRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService service;

    @PostMapping("/create")
    public void create(@RequestBody List<UserRequest> req) {

        List<UserInpRequest> list = req.stream()
                .map(r -> new UserInpRequest(
                        r.getPhoneNumber(), "", r.getCccd(), r.getFullname(), r.getEmail(), "", r.getId(), ""
                ))
                .toList();

        service.create(list);
    }
}
