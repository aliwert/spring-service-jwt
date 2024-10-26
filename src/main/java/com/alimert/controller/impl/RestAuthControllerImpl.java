package com.alimert.controller.impl;

import com.alimert.controller.IRestAuthController;
import com.alimert.dto.DtoUser;
import com.alimert.jwt.AuthRequest;
import com.alimert.service.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAuthControllerImpl implements IRestAuthController {

    @Autowired
    private IAuthService authService;


    @PostMapping(path = "/register")
    @Override
    public DtoUser register(@Valid @RequestBody AuthRequest authRequest) {
        return authService.register(authRequest);
    }
}
