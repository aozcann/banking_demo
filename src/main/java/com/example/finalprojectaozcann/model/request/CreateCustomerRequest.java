package com.example.finalprojectaozcann.model.request;

import com.example.finalprojectaozcann.model.enums.CustomerType;
import com.example.finalprojectaozcann.model.enums.RoleType;

import java.util.Set;

public record CreateCustomerRequest(String name,
                                    String surname,
                                    Long identityNumber,
                                    String birthday,
                                    String phoneNumber,
                                    String address,
                                    String email,
                                    String password,
                                    CustomerType customerType,
                                    Set<RoleType> roles
) {
}
