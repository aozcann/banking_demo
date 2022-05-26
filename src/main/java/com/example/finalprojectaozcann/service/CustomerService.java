package com.example.finalprojectaozcann.service;

import com.example.finalprojectaozcann.model.request.CreateCustomerRequest;
import com.example.finalprojectaozcann.model.request.UpdateCustomerRequest;
import com.example.finalprojectaozcann.model.response.CreateCustomerResponse;
import com.example.finalprojectaozcann.model.response.GetCustomerResponse;

import java.util.Collection;

public interface CustomerService {

    CreateCustomerResponse create(CreateCustomerRequest request);

    Collection<GetCustomerResponse> getAllCustomer();

    GetCustomerResponse updateCustomer(UpdateCustomerRequest request, Long id);

    GetCustomerResponse getCustomer(Long id);

    boolean deleteCustomerById(Long id, boolean isHardDeleted);
}
