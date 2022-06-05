package com.example.finalprojectaozcann.model.response;

import com.example.finalprojectaozcann.model.entity.Role;

import java.util.Set;

public record GenerateAdminUserResponse(Long identityNumber,
                                        String password,
                                        Set<Role> role) {
}
