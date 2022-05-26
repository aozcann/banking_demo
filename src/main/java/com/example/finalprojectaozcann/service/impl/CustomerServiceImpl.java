package com.example.finalprojectaozcann.service.impl;

import com.example.finalprojectaozcann.converter.CustomerConverter;
import com.example.finalprojectaozcann.exception.BaseException;
import com.example.finalprojectaozcann.exception.BusinessServiceOperationException;
import com.example.finalprojectaozcann.model.entity.Customer;
import com.example.finalprojectaozcann.model.request.CreateCustomerRequest;
import com.example.finalprojectaozcann.model.request.UpdateCustomerRequest;
import com.example.finalprojectaozcann.model.response.CreateCustomerResponse;
import com.example.finalprojectaozcann.model.response.GetCustomerResponse;
import com.example.finalprojectaozcann.repository.CustomerRepository;
import com.example.finalprojectaozcann.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;

    public CreateCustomerResponse create(CreateCustomerRequest request) {
        Customer customer = customerConverter.toCreateCustomer(request);
        customerRepository.save(customer);

        log.info("Customer created successfully by id -> {}", customer.getId());
        return new CreateCustomerResponse(customer.getId());
    }

    @Override
    public Collection<GetCustomerResponse> getAllCustomer() {

        List<GetCustomerResponse> customerResponseList = customerRepository.findAllByIsDeleted(false)
                .stream()
                .map(customerConverter::toGetCustomerResponse)
                .toList();
        log.info("Customer list returned successfully.");
        return customerResponseList;
    }

    @Override
    public GetCustomerResponse updateCustomer(UpdateCustomerRequest request, Long id) throws BaseException {
        Customer customer = customerRepository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new BusinessServiceOperationException.CustomerNotFoundException("Customer not found"));
        Customer updateCustomer = customerConverter.toUpdateCustomer(request, customer);
        customerRepository.save(updateCustomer);
        log.info("Customer updated successfully by id -> {}", customer.getId());
        return customerConverter.toGetCustomerResponse(customer);
    }

    @Override
    public GetCustomerResponse getCustomer(Long id) throws BaseException {
        Customer customer = customerRepository
                .findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new BusinessServiceOperationException.CustomerNotFoundException("Customer not found"));
        log.info("Customer returned successfully by id -> {}", customer.getId());
        return customerConverter.toGetCustomerResponse(customer);
    }

    @Override
    public boolean deleteCustomerById(Long id, boolean isHardDeleted) throws BaseException {
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new BusinessServiceOperationException.CustomerNotFoundException("Customer not found."));
        if (isHardDeleted) {
            customerRepository.delete(customer);
            log.info("Customer hard deleted successfully.");
            return true;
        }
        if (customer.isDeleted()) {
            log.error("Customer id:{} already deleted.", id);
            throw new BusinessServiceOperationException.CustomerAlreadyDeletedException("Customer already deleted.");
        }
        customer.setDeleted(true);
        //TODO rol ve account delete eklenecek
        customer.setDeletedAt(new Date());
        customer.setDeletedBy("AhmetOzcan");
        customerRepository.save(customer);
        log.info("Customer soft deleted successfully by id -> {}", customer.getId());

        return customer.isDeleted();
    }


}