package com.alimert.service;

import com.alimert.dto.DtoUser;
import com.alimert.jwt.AuthRequest;
import com.alimert.jwt.AuthResponse;

public interface IAuthService {

    public DtoUser register(AuthRequest authRequest);
    public AuthResponse authenticate(AuthRequest authRequest);
}
