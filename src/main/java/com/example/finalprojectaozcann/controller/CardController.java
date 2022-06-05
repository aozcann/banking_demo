package com.example.finalprojectaozcann.controller;

import com.example.finalprojectaozcann.model.request.CardExtractRequest;
import com.example.finalprojectaozcann.model.request.CreateCardRequest;
import com.example.finalprojectaozcann.model.request.DebitCardDeptInquiryRequest;
import com.example.finalprojectaozcann.model.response.GetBankCardResponse;
import com.example.finalprojectaozcann.model.response.GetCardExtractResponse;
import com.example.finalprojectaozcann.model.response.GetDebitCardDeptInquiryResponse;
import com.example.finalprojectaozcann.model.response.GetDebitCardResponse;
import com.example.finalprojectaozcann.service.CardService;
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
@RequestMapping("/api/cards")
public class CardController {

    private final Validator<CreateCardRequest> createCardRequestValidator;
    private final Validator<DebitCardDeptInquiryRequest> debitCardDeptInquiryRequestValidator;
    private final CardService cardService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = "/bank")
    public ResponseEntity<GetBankCardResponse> createBankCard(@RequestBody CreateCardRequest request, HttpServletRequest httpServletRequest) {
        createCardRequestValidator.validate(request);
        return ResponseEntity.ok(cardService.createBankCard(request, httpServletRequest));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = "/debit")
    public ResponseEntity<GetDebitCardResponse> createDebitCard(@RequestBody CreateCardRequest request, HttpServletRequest httpServletRequest) {
        createCardRequestValidator.validate(request);
        return ResponseEntity.ok(cardService.createDebitCard(request, httpServletRequest));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = "/debit/inquiry")
    public ResponseEntity<GetDebitCardDeptInquiryResponse> getInquiryDebitCard(@RequestBody DebitCardDeptInquiryRequest request, HttpServletRequest httpServletRequest) {
        debitCardDeptInquiryRequestValidator.validate(request);
        return ResponseEntity.ok(cardService.getInquiryDebitCard(request, httpServletRequest));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = "/debit/extract")
    public ResponseEntity<GetCardExtractResponse> getExtractOfDebitCard(@RequestBody CardExtractRequest request, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(cardService.getExtractOfDebitCard(request, httpServletRequest));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = "/bank/extract")
    public ResponseEntity<GetCardExtractResponse> getExtractOfBankCard(@RequestBody CardExtractRequest request, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(cardService.getExtractOfBankCard(request, httpServletRequest));
    }


}
