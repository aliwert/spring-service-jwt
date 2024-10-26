package com.alimert.service.impl;

import com.alimert.dto.DtoUser;
import com.alimert.jwt.AuthRequest;
import com.alimert.model.User;
import com.alimert.repository.UserRepository;
import com.alimert.service.IAuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public DtoUser register(AuthRequest authRequest) {
        DtoUser dtoUser = new DtoUser();
        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(authRequest.getPassword());
        User savedUser = userRepository.save(user);
        BeanUtils.copyProperties(savedUser, dtoUser);



        return dtoUser;
    }
}
