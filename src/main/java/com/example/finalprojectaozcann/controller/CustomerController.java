package com.example.finalprojectaozcann.controller;

import com.example.finalprojectaozcann.model.request.CreateCustomerRequest;
import com.example.finalprojectaozcann.model.request.UpdateCustomerRequest;
import com.example.finalprojectaozcann.model.response.GetCustomerResponse;
import com.example.finalprojectaozcann.service.CustomerService;
import com.example.finalprojectaozcann.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final Validator<CreateCustomerRequest> createCustomerRequestValidator;
    private final Validator<Long> idValidator;
    private final CustomerService customerService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody CreateCustomerRequest request) {
        createCustomerRequestValidator.validate(request);
        return ResponseEntity.ok(customerService.create(request));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<Collection<GetCustomerResponse>> getAllCustomer() {
        return ResponseEntity.ok(customerService.getAllCustomer());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCustomerResponse> getCustomer(@PathVariable Long id) {
        idValidator.validate(id);
        return ResponseEntity.ok(customerService.getCustomer(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetCustomerResponse> update(@RequestBody UpdateCustomerRequest request,
                                                      @PathVariable Long id) {
        idValidator.validate(id);
        return ResponseEntity.ok(customerService.updateCustomer(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Long id,
                                                @RequestParam(name = "isHardDeleted", required = false) boolean isHardDeleted) {
        idValidator.validate(id);
        return ResponseEntity.ok(customerService.deleteCustomerById(id, isHardDeleted));
    }


}
