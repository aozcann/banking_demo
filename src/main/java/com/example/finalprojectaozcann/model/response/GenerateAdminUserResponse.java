package com.example.finalprojectaozcann.model.response;

import com.example.finalprojectaozcann.model.entity.Role;

import java.util.Set;

public record GenerateAdminUserResponse(String password,
                                        Set<Role> role) {
}
