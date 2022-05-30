package com.example.finalprojectaozcann.service;

import com.example.finalprojectaozcann.model.request.CreateUserRequest;
import com.example.finalprojectaozcann.model.request.UpdateUserRequest;
import com.example.finalprojectaozcann.model.response.CreateUserResponse;
import com.example.finalprojectaozcann.model.response.GenerateAdminUserResponse;
import com.example.finalprojectaozcann.model.response.GetUserResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public interface UserService {

    CreateUserResponse create(CreateUserRequest request, HttpServletRequest httpServletRequest);

    Collection<GetUserResponse> getAllUser();

    GetUserResponse updateUser(UpdateUserRequest request, Long id, HttpServletRequest httpServletRequest);

    GetUserResponse getUser(Long id);

    boolean deleteUserById(Long id, boolean isHardDeleted, HttpServletRequest httpServletRequest);

    GenerateAdminUserResponse generateAdminUser();
}
