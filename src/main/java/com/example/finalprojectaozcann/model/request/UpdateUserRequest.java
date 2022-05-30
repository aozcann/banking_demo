package com.example.finalprojectaozcann.model.request;

import com.example.finalprojectaozcann.model.enums.RoleType;
import com.example.finalprojectaozcann.model.enums.UserStatus;
import com.example.finalprojectaozcann.model.enums.UserType;

import java.util.Set;

public record UpdateUserRequest(String name,
                                String surname,
                                Long identityNumber,
                                String birthday,
                                String phoneNumber,
                                String address,
                                String email,
                                String password,
                                UserStatus userStatus,
                                UserType userType,
                                Set<RoleType> roles) {
}
