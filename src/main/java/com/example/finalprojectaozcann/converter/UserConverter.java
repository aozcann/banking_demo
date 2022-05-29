package com.example.finalprojectaozcann.converter;

import com.example.finalprojectaozcann.model.entity.User;
import com.example.finalprojectaozcann.model.entity.Role;
import com.example.finalprojectaozcann.model.enums.RoleType;
import com.example.finalprojectaozcann.model.request.CreateUserRequest;
import com.example.finalprojectaozcann.model.request.UpdateUserRequest;
import com.example.finalprojectaozcann.model.response.GetUserResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class UserConverter {


    public User toCreateUser(CreateUserRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setSurname(request.surname());
        user.setIdentityNumber(request.identityNumber());


        user.setBirthday(dateFormat(request.birthday()));
        user.setPhoneNumber(request.phoneNumber());
        user.setAddress(request.address());
        user.setEmail(request.email());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setUserType(request.userType());

        Set<Role> roleSet = new HashSet<>();
        for (RoleType role : request.roles()) {
            Role newRole = new Role();
            if (role.equals(RoleType.ADMIN)) {
                newRole.setName(RoleType.ADMIN.toString());
            }
            if (role.equals(RoleType.USER)) {
                newRole.setName(RoleType.USER.toString());
            }
            roleSet.add(newRole);
        }
        user.setRoles(roleSet);
        user.setCreatedBy("AhmetOzcan");
        user.setCreatedAt(new Date());

        return user;

    }

    public GetUserResponse toGetUserResponse(User user) {
        return new GetUserResponse(user.getId(),
                user.getFullName(),
                user.getIdentityNumber(),
                user.getBirthday(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getStatus(),
                user.getUserType(),
                new HashSet<>(user.getRoles()));
    }

    public User toUpdateUser(UpdateUserRequest request, User user) {
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
            user.setBirthday(dateFormat(request.birthday()));
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
            //TODO
        }
        user.setUpdatedAt(new Date());
        user.setUpdatedBy("AhmetOzcan");
        return user;
    }

    public LocalDate dateFormat(String birthday) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        LocalDate ld = LocalDate.parse(birthday, f);
        return ld;
    }
}
