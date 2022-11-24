package net.backend.paylens.services;

import net.backend.paylens.model.dto.request.TopUpDto;
import net.backend.paylens.model.dto.request.TransactionDto;
import net.backend.paylens.model.dto.request.TransferDto;
import net.backend.paylens.model.dto.response.ResponseData;
import net.backend.paylens.model.entity.TopUp;
import net.backend.paylens.model.entity.Transfer;

public interface TransactionService {
    ResponseData<Object> topUpMoney(TopUpDto amount) throws Exception;

    ResponseData<Object> transfer(TransferDto data);

    ResponseData<Object> updateBalance(TopUpDto request);
}
