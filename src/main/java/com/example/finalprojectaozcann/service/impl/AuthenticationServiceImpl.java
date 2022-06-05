package com.example.finalprojectaozcann.service.impl;

import com.example.finalprojectaozcann.model.request.LoginRequest;
import com.example.finalprojectaozcann.model.response.LoginResponse;
import com.example.finalprojectaozcann.security.JWTHelper;
import com.example.finalprojectaozcann.security.UserDetail;
import com.example.finalprojectaozcann.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JWTHelper jwtHelper;

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.identityNumber().toString(), request.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        String token = jwtHelper.generateToken(request.identityNumber(), userDetail);
        return new LoginResponse(token,
                userDetail
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet()));
    }


}
