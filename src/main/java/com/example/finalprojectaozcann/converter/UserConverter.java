package com.example.finalprojectaozcann.converter;

import com.example.finalprojectaozcann.model.entity.Role;
import com.example.finalprojectaozcann.model.entity.User;
import com.example.finalprojectaozcann.model.enums.RoleType;
import com.example.finalprojectaozcann.model.request.CreateUserRequest;
import com.example.finalprojectaozcann.model.request.UpdateUserRequest;
import com.example.finalprojectaozcann.model.response.GetUserResponse;
import com.example.finalprojectaozcann.utils.DateUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class UserConverter {

    public User toCreateUser(CreateUserRequest request, Long loggedUserId) {
        User user = new User();
        user.setName(request.name());
        user.setSurname(request.surname());
        user.setIdentityNumber(request.identityNumber());

        user.setBirthday(DateUtil.dateFormatStringToLocalDate(request.birthday()));
        user.setPhoneNumber(request.phoneNumber());
        user.setAddress(request.address());
        user.setEmail(request.email());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setUserType(request.userType());

        user.setRoles(createRoleForUser(request.roles()));
        user.setCreatedBy(loggedUserId.toString());
        user.setCreatedAt(new Date());

        return user;
    }

    public GetUserResponse toGetUserResponse(User user) {
        return new GetUserResponse(user.getId(),
                user.getIdentityNumber(),
                user.getBirthday(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getStatus(),
                user.getUserType(),
                new HashSet<>(user.getRoles()));
    }

    public User toUpdateUser(UpdateUserRequest request, User user, Long loggedUserId) {
        if (Objects.nonNull(request.name())) {
            user.setName(request.name());
        }
        if (Objects.nonNull(request.surname())) {
            user.setSurname(request.surname());
        }
        if (Objects.nonNull(request.identityNumber())) {
            user.setIdentityNumber(request.identityNumber());
        }
        if (Objects.nonNull(request.birthday())) {
            user.setBirthday(DateUtil.dateFormatStringToLocalDate(request.birthday()));
        }
        if (Objects.nonNull(request.phoneNumber())) {
            user.setPhoneNumber(request.phoneNumber());
        }
        if (Objects.nonNull(request.address())) {
            user.setAddress(request.address());
        }
        if (Objects.nonNull(request.email())) {
            user.setEmail(request.email());
        }
        if (Objects.nonNull(request.userStatus())) {
            user.setStatus(request.userStatus());
        }
        if (Objects.nonNull(request.userType())) {
            user.setUserType(request.userType());
        }
        if (Objects.nonNull(request.roles())) {
            user.setRoles(createRoleForUser(request.roles()));
        }
        user.setUpdatedAt(new Date());
        user.setUpdatedBy(loggedUserId.toString());
        return user;
    }

    public Set<Role> createRoleForUser(Set<RoleType> roles) {
        Set<Role> roleSet = new HashSet<>();
        for (RoleType role : roles) {
            Role newRole = new Role();
            if (role.equals(RoleType.ADMIN)) {
                newRole.setName(RoleType.ADMIN.toString());
            }
            if (role.equals(RoleType.USER)) {
                newRole.setName(RoleType.USER.toString());
            }
            roleSet.add(newRole);
        }
        return roleSet;
    }

}
