package net.backend.paylens.service;

import net.backend.paylens.model.dto.request.TopUpDto;
import net.backend.paylens.model.dto.request.TransferDto;
import net.backend.paylens.model.dto.response.ResponseData;

public interface TransactionService {
    ResponseData<Object> topUpMoney(long id,TopUpDto request) throws Exception;

    ResponseData<Object> transfer(long id,TransferDto data) throws Exception;
}
