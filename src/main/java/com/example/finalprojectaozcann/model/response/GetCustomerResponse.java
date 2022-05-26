package com.example.finalprojectaozcann.model.response;

import com.example.finalprojectaozcann.model.entity.Role;
import com.example.finalprojectaozcann.model.enums.CustomerStatus;
import com.example.finalprojectaozcann.model.enums.CustomerType;

import java.time.LocalDate;
import java.util.Set;

public record GetCustomerResponse(Long id,
                                  String fullName,
                                  Long identityNumber,
                                  LocalDate birthday,
                                  String phoneNumber,
                                  String email,
                                  CustomerStatus status,
                                  CustomerType customerType,
                                  Set<Role> roles) {
}
