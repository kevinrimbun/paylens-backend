package net.backend.paylens.service;

import net.backend.paylens.model.dto.request.TransactionDto;
import net.backend.paylens.model.dto.response.ResponseData;

public interface TransactionService {

    // Top up method
    ResponseData<Object> topUpMoney(TransactionDto amount);

    // Transfer method
    ResponseData<Object> transfer(TransactionDto kevalapundaklututkaki);
}
