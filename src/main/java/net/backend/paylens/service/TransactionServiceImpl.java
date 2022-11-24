package net.backend.paylens.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.backend.paylens.model.dto.request.TopUpDto;
import net.backend.paylens.model.dto.request.TransactionDto;
import net.backend.paylens.model.dto.request.TransferDto;
import net.backend.paylens.model.dto.response.ResponseData;
import net.backend.paylens.model.entity.Balance;
import net.backend.paylens.model.entity.TopUp;
import net.backend.paylens.model.entity.Transfer;
import net.backend.paylens.model.entity.User;
import net.backend.paylens.repository.BalanceRepository;
import net.backend.paylens.repository.TopUpRepository;
import net.backend.paylens.repository.TransferRepository;
import net.backend.paylens.repository.UserRepository;
import net.backend.paylens.validator.UserValidator;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private TopUpRepository topUpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserValidator userValidator;

    private TopUp topUp;
    private Balance balance;
    private Transfer transfer;
    private ResponseData<Object> responseData;
    private Map<Object, Object> data;
    private List<Object> object;

    @Override
    public ResponseData<Object> topUpMoney(TopUpDto amount) throws Exception {

        Optional<Balance> balanceOpt = balanceRepository.findById(amount.getId());
        if (balanceOpt.isPresent()) {
            updateBalance(amount.getAmount(), transfer);
            responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Top Up updated", topUp.getTopAmount());
        }else{
            balance = new Balance();
            topUp = new TopUp();
            topUp.setTopAmount(amount.getAmount());
            topUp.setNotes(amount.getNotes());
            // Long jumlah = balance.getMoney();
            balance.setMoney(amount.getAmount());
            // Long total = jumlah + amount.getAmount();

            topUpRepository.save(topUp);
            balanceRepository.save(balance);
            responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Top Up success", topUp.getTopAmount());
        }
        return responseData;
    }

    private void updateBalance(Long amount, Transfer transfer2) {
    }

    @Override
    public ResponseData<Object> transfer(TransferDto data) {
        // TODO Auto-generated method stub


        responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Transfer success", transfer);
        return responseData;
    }

    @Override
    public ResponseData<Object> updateBalance(TopUpDto amount) {
        // TODO Auto-generated method stub
        Optional<Balance> balanceOpt = balanceRepository.findById(amount.getId());
        if (balanceOpt.isPresent()) {
            balance = balanceOpt.get();
            topUp.setTopAmount(amount.getAmount());
            topUp.setNotes(amount.getNotes());
            // Long jumlah = balance.getMoney();
            balance.setMoney(amount.getAmount());
            // Long total = jumlah + amount.getAmount();

            topUpRepository.save(topUp);
            balanceRepository.save(balance);
            responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Top Up updated", topUp.getTopAmount());
        }else{
            
            responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Top Up success", topUp.getTopAmount());
        }
        return responseData;
    }
    
}
