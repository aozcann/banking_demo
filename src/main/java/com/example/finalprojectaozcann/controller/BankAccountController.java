package com.example.finalprojectaozcann.controller;

import com.example.finalprojectaozcann.model.request.CreateCheckingAccountRequest;
import com.example.finalprojectaozcann.model.request.CreateDepositAccountRequest;
import com.example.finalprojectaozcann.service.BankAccountService;
import com.example.finalprojectaozcann.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class BankAccountController {

    private final Validator<CreateCheckingAccountRequest> createCheckingAccountRequestValidator;
    private final Validator<CreateDepositAccountRequest> createDepositAccountRequestValidator;
    private final Validator<Long> idValidator;
    private final BankAccountService bankAccountService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = "/checking")
    public ResponseEntity<?> createCheckingAccount(@RequestBody CreateCheckingAccountRequest request, HttpServletRequest httpServletRequest) {
        createCheckingAccountRequestValidator.validate(request);
        return ResponseEntity.ok(bankAccountService.createChecking(request, httpServletRequest));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = "/deposit")
    public ResponseEntity<?> createDepositAccount(@RequestBody CreateDepositAccountRequest request, HttpServletRequest httpServletRequest) {
        createDepositAccountRequestValidator.validate(request);
        return ResponseEntity.ok(bankAccountService.createDeposit(request, httpServletRequest));
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/checking/{id}")
    public ResponseEntity<?> deleteCheckingAccountById(@PathVariable Long id,
                                                       HttpServletRequest httpServletRequest) {
        idValidator.validate(id);
        return ResponseEntity.ok(bankAccountService.deleteCheckingAccountById(id, httpServletRequest));
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/deposit/{id}")
    public ResponseEntity<?> deleteDepositAccountById(@PathVariable Long id,
                                                      HttpServletRequest httpServletRequest) {
        idValidator.validate(id);
        return ResponseEntity.ok(bankAccountService.deleteDepositAccountById(id, httpServletRequest));
    }
}
