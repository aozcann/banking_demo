package com.example.finalprojectaozcann.model.response;

import com.example.finalprojectaozcann.model.entity.TransferHistory;

import java.util.Collection;

public record GetCardExtractResponse(
        Collection<TransferHistory> transferHistorySet) {
}
