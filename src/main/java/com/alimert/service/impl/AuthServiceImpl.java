package com.alimert.service.impl;

import com.alimert.dto.DtoUser;
import com.alimert.jwt.AuthRequest;
import com.alimert.jwt.AuthResponse;
import com.alimert.jwt.JwtService;
import com.alimert.model.User;
import com.alimert.repository.UserRepository;
import com.alimert.service.IAuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtService jwtService;


    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        try {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
            authenticationProvider.authenticate(auth);// checking db username and password

            Optional<User> optionalUser = userRepository.findByUsername(authRequest.getUsername());
            String token = jwtService.generateToken(optionalUser.get());
            return new AuthResponse(token);
        } catch (Exception e) {
            System.out.println(("Username and password incorrect" + e.getMessage()));
        }
        return null;
    }

    @Override
    public DtoUser register(AuthRequest authRequest) {
        DtoUser dtoUser = new DtoUser();
        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(encoder.encode(authRequest.getPassword()));
        User savedUser = userRepository.save(user);
        BeanUtils.copyProperties(savedUser, dtoUser);


        return dtoUser;
    }


}
