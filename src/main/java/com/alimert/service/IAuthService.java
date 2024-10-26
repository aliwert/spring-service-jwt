package com.alimert.service;

import com.alimert.dto.DtoUser;
import com.alimert.jwt.AuthRequest;

public interface IAuthService {

    public DtoUser register(AuthRequest authRequest);
}
