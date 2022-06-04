package com.example.finalprojectaozcann.model.response;

import com.example.finalprojectaozcann.model.entity.Role;
import com.example.finalprojectaozcann.model.enums.UserStatus;
import com.example.finalprojectaozcann.model.enums.UserType;

import java.time.LocalDate;
import java.util.Set;

public record GetUserResponse(Long id,
                              Long identityNumber,
                              LocalDate birthday,
                              String phoneNumber,
                              String email,
                              UserStatus status,
                              UserType userType,
                              Set<Role> roles) {
}
