package com.alimert.controller;


import com.alimert.dto.DtoUser;
import com.alimert.jwt.AuthRequest;
import org.springframework.stereotype.Controller;

@Controller
public interface IRestAuthController {
    public DtoUser register(AuthRequest authRequest);
}
