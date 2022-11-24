package net.backend.paylens.services;

import net.backend.paylens.model.dto.request.TransactionDto;
import net.backend.paylens.model.dto.response.ResponseData;

public interface TransactionService {
    ResponseData<Object> topUpMoney(TransactionDto amount);

    ResponseData<Object> transfer(TransactionDto kevalapundaklututkaki);
}
