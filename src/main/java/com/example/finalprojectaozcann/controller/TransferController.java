package com.example.finalprojectaozcann.controller;

import com.example.finalprojectaozcann.model.request.ShoppingWithCardRequest;
import com.example.finalprojectaozcann.model.request.TransferATMToCardRequest;
import com.example.finalprojectaozcann.model.request.TransferCheckingAccountToDebitCardRequest;
import com.example.finalprojectaozcann.model.request.TransferToAccountRequest;
import com.example.finalprojectaozcann.model.response.SuccessATMTransferResponse;
import com.example.finalprojectaozcann.model.response.SuccessAccountTransferResponse;
import com.example.finalprojectaozcann.model.response.SuccessCardTransferResponse;
import com.example.finalprojectaozcann.model.response.SuccessShoppingResponse;
import com.example.finalprojectaozcann.service.TransferService;
import com.example.finalprojectaozcann.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;
    private final Validator<TransferToAccountRequest> transferToAccountRequestValidator;
    private final Validator<TransferCheckingAccountToDebitCardRequest> transferCheckingAccountToDebitCardRequestValidator;
    private final Validator<TransferATMToCardRequest> transferATMToCardRequestValidator;
    private final Validator<ShoppingWithCardRequest> shoppingWithCardRequestValidator;
    private final Validator<String> dateRequestValidator;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = "/checking/deposit")
    public ResponseEntity<SuccessAccountTransferResponse> transferCheckingToDeposit(@RequestBody TransferToAccountRequest request,
                                                                                    HttpServletRequest httpServletRequest) {
        transferToAccountRequestValidator.validate(request);
        dateRequestValidator.validate(request.transferDate());
        return ResponseEntity.ok(transferService.transferCheckingToDeposit(request, httpServletRequest));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = "/deposit/checking")
    public ResponseEntity<SuccessAccountTransferResponse> transferDepositToChecking(@RequestBody TransferToAccountRequest request,
                                                                                    HttpServletRequest httpServletRequest) {
        transferToAccountRequestValidator.validate(request);
        dateRequestValidator.validate(request.transferDate());
        return ResponseEntity.ok(transferService.transferDepositToChecking(request, httpServletRequest));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = "/checking/checking")
    public ResponseEntity<SuccessAccountTransferResponse> transferCheckingToChecking(@RequestBody TransferToAccountRequest request,
                                                                                     HttpServletRequest httpServletRequest) {
        transferToAccountRequestValidator.validate(request);
        dateRequestValidator.validate(request.transferDate());
        return ResponseEntity.ok(transferService.transferCheckingToChecking(request, httpServletRequest));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = "/checking/debit-card")
    public ResponseEntity<SuccessCardTransferResponse> transferCheckingToDebitCard(@RequestBody TransferCheckingAccountToDebitCardRequest request,
                                                                                   HttpServletRequest httpServletRequest) {
        transferCheckingAccountToDebitCardRequestValidator.validate(request);
        dateRequestValidator.validate(request.transferDate());
        return ResponseEntity.ok(transferService.transferCheckingToDebitCard(request, httpServletRequest));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = "/atm/debit-card")
    public ResponseEntity<SuccessATMTransferResponse> transferATMToDebitCard(@RequestBody TransferATMToCardRequest request,
                                                                             HttpServletRequest httpServletRequest) {
        transferATMToCardRequestValidator.validate(request);
        return ResponseEntity.ok(transferService.transferATMToDebitCard(request, httpServletRequest));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = "/atm/bank-card")
    public ResponseEntity<SuccessATMTransferResponse> transferATMToBankCard(@RequestBody TransferATMToCardRequest request,
                                                                            HttpServletRequest httpServletRequest) {
        transferATMToCardRequestValidator.validate(request);
        return ResponseEntity.ok(transferService.transferATMToBankCard(request, httpServletRequest));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = "/debit-card/shopping")
    public ResponseEntity<SuccessShoppingResponse> shoppingWithDebitCard(@RequestBody ShoppingWithCardRequest request, HttpServletRequest httpServletRequest) {
        shoppingWithCardRequestValidator.validate(request);
        return ResponseEntity.ok(transferService.shoppingWithDebitCard(request, httpServletRequest));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = "/bank-card/shopping")
    public ResponseEntity<SuccessShoppingResponse> shoppingWithBankCard(@RequestBody ShoppingWithCardRequest request, HttpServletRequest httpServletRequest) {
        shoppingWithCardRequestValidator.validate(request);
        return ResponseEntity.ok(transferService.shoppingWithBankCard(request, httpServletRequest));
    }

}
