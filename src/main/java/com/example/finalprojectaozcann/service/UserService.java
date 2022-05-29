package com.example.finalprojectaozcann.service;

import com.example.finalprojectaozcann.model.request.CreateUserRequest;
import com.example.finalprojectaozcann.model.request.UpdateUserRequest;
import com.example.finalprojectaozcann.model.response.CreateUserResponse;
import com.example.finalprojectaozcann.model.response.GenerateAdminUserResponse;
import com.example.finalprojectaozcann.model.response.GetUserResponse;

import java.util.Collection;

public interface UserService {

    CreateUserResponse create(CreateUserRequest request);

    Collection<GetUserResponse> getAllUser();

    GetUserResponse updateUser(UpdateUserRequest request, Long id);

    GetUserResponse getUser(Long id);

    boolean deleteUserById(Long id, boolean isHardDeleted);

    GenerateAdminUserResponse generateAdminUser();
}
