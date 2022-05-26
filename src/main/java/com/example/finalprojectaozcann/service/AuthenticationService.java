package com.example.finalprojectaozcann.service;

import com.example.finalprojectaozcann.model.request.LoginRequest;
import com.example.finalprojectaozcann.model.response.LoginResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request);
}
