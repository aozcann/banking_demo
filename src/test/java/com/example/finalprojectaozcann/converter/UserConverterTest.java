package com.example.finalprojectaozcann.converter;

import com.example.finalprojectaozcann.model.entity.Role;
import com.example.finalprojectaozcann.model.entity.User;
import com.example.finalprojectaozcann.model.enums.RoleType;
import com.example.finalprojectaozcann.model.enums.UserStatus;
import com.example.finalprojectaozcann.model.enums.UserType;
import com.example.finalprojectaozcann.model.response.GetUserResponse;
import com.example.finalprojectaozcann.utils.DateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserConverterTest {

    @InjectMocks
    UserConverter userConverter;

    @Test
    void should_returnGetUserResponse_when_toGetUserResponseMethod() {

        User user = createUser();

        GetUserResponse expectedGetUserResponse = new GetUserResponse(user.getId(),
                user.getIdentityNumber(),
                user.getBirthday(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getStatus(),
                user.getUserType(),
                new HashSet<>(user.getRoles()));

        GetUserResponse actualGetUserResponse = userConverter.toGetUserResponse(user);

        assertThat(expectedGetUserResponse).isEqualTo(actualGetUserResponse);

    }

    public User createUser() {
        User user = new User();

        user.setName("Ahmet");
        user.setSurname("Ozcan");
        user.setIdentityNumber(12345678912L);
        user.setBirthday(DateUtil.dateFormatStringToLocalDate("11/09/1994"));
        user.setEmail("ozcanahmet94@gmail.com");
        user.setAddress("Ankara");
        user.setPhoneNumber("05419322436");
        user.setStatus(UserStatus.ACTIVE);
        user.setUserType(UserType.INDIVIDUAL);
        user.setCreatedBy("1");
        user.setCreatedAt(new Date());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode("a1b2c3"));

        Role role = new Role();
        role.setName(RoleType.USER.toString());
        user.setRoles(new HashSet<>(List.of(role)));

        return user;
    }


}

