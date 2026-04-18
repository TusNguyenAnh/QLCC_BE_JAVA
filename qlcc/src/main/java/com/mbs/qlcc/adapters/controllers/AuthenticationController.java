package com.mbs.qlcc.adapters.controllers;

import com.mbs.qlcc.adapters.request.Authentication.AuthenticationRequest;
import com.mbs.qlcc.adapters.services.AuthenticationService;
import com.mbs.qlcc.usecases.request.Authentication.AuthenticationInpRequest;
import com.mbs.qlcc.usecases.response.Authentication.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authen")
public class AuthenticationController {
    @Autowired
    AuthenticationService service;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest req) {
        return service.login(
                new AuthenticationInpRequest(req.getUsername(), req.getPasswordRaw(), req.getComplexId(), req.getOrgId())
        );
    }
}