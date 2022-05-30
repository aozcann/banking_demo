package com.example.finalprojectaozcann.model.request;

import com.example.finalprojectaozcann.model.enums.RoleType;
import com.example.finalprojectaozcann.model.enums.UserType;

import java.util.Set;

public record CreateUserRequest(String name,
                                String surname,
                                Long identityNumber,
                                String birthday,
                                String phoneNumber,
                                String address,
                                String email,
                                String password,
                                UserType userType,
                                Set<RoleType> roles
) {
}
