package com.example.finalprojectaozcann.converter;

import com.example.finalprojectaozcann.model.entity.Customer;
import com.example.finalprojectaozcann.model.entity.Role;
import com.example.finalprojectaozcann.model.enums.RoleType;
import com.example.finalprojectaozcann.model.request.CreateCustomerRequest;
import com.example.finalprojectaozcann.model.request.UpdateCustomerRequest;
import com.example.finalprojectaozcann.model.response.GetCustomerResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class CustomerConverter {


    public Customer toCreateCustomer(CreateCustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setSurname(request.surname());
        customer.setIdentityNumber(request.identityNumber());


        customer.setBirthday(dateFormat(request.birthday()));
        customer.setPhoneNumber(request.phoneNumber());
        customer.setAddress(request.address());
        customer.setEmail(request.email());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        customer.setPassword(passwordEncoder.encode(request.password()));
        customer.setCustomerType(request.customerType());

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
        customer.setRoles(roleSet);
        customer.setCreatedBy("AhmetOzcan");
        customer.setCreatedAt(new Date());

        return customer;

    }

    public GetCustomerResponse toGetCustomerResponse(Customer customer) {
        return new GetCustomerResponse(customer.getId(),
                customer.getFullName(),
                customer.getIdentityNumber(),
                customer.getBirthday(),
                customer.getPhoneNumber(),
                customer.getEmail(),
                customer.getStatus(),
                customer.getCustomerType(),
                new HashSet<>(customer.getRoles()));
    }

    public Customer toUpdateCustomer(UpdateCustomerRequest request, Customer customer) {
        if (Objects.nonNull(request.name())) {
            customer.setName(request.name());
        }
        if (Objects.nonNull(request.surname())) {
            customer.setSurname(request.surname());
        }
        if (Objects.nonNull(request.identityNumber())) {
            customer.setIdentityNumber(request.identityNumber());
        }
        if (Objects.nonNull(request.birthday())) {
            customer.setBirthday(dateFormat(request.birthday()));
        }
        if (Objects.nonNull(request.phoneNumber())) {
            customer.setPhoneNumber(request.phoneNumber());
        }
        if (Objects.nonNull(request.address())) {
            customer.setAddress(request.address());
        }
        if (Objects.nonNull(request.email())) {
            customer.setEmail(request.email());
        }
        if (Objects.nonNull(request.customerStatus())) {
            customer.setStatus(request.customerStatus());
        }
        if (Objects.nonNull(request.customerType())) {
            customer.setCustomerType(request.customerType());
        }
        if (Objects.nonNull(request.roles())) {
            //TODO
        }
        customer.setUpdatedAt(new Date());
        customer.setUpdatedBy("AhmetOzcan");
        return customer;
    }

    public LocalDate dateFormat(String birthday) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        LocalDate ld = LocalDate.parse(birthday, f);
        return ld;
    }
}
