package net.backend.paylens.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.backend.paylens.model.dto.request.TransactionDto;
import net.backend.paylens.model.dto.response.ResponseData;
import net.backend.paylens.model.entity.Balance;
import net.backend.paylens.model.entity.Transfer;
import net.backend.paylens.repository.BalanceRepository;
import net.backend.paylens.repository.TransferRepository;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService{

    // Construct repository
    @Autowired
    private BalanceRepository balanceRepository;
    @Autowired
    private TransferRepository transferRepository;

    // Attribute
    private Balance balance;
    private Transfer transfer;
    private Map<Object, Object> data;
    private List<Object> object;
    private ResponseData<Object> responseData;

    // Top up method
    @Override
    public ResponseData<Object> topUpMoney(TransactionDto amount) {

            // Instance balance object
            balance = new Balance();

            Long jumlah = balance.getMoney();

            // Set balance
            balance.setMoney(amount.getAmount());
            Long total = jumlah + amount.getAmount();

            // Save to database
            balanceRepository.save(balance);
            // Response data
            responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Top Up success", balance.getMoney());
        return responseData;
    }

    @Override
    public ResponseData<Object> transfer(TransactionDto kevalapundaklututkaki) {
        return null;
    }
    
}
