package net.backend.paylens.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.backend.paylens.model.dto.request.TopUpDto;
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
import net.backend.paylens.validator.BalanceValidator;
import net.backend.paylens.validator.UserValidator;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService{

    // Construct repository and validator
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
    @Autowired
    private BalanceValidator balanceValidator;

    // Attribute
    private User user;
    private TopUp topUp;
    private Transfer transfer;
    private Balance balance;
    private Balance balanceReceiver;
    private ResponseData<Object> responseData;

    //  Top up method
    @Override
    public ResponseData<Object> topUpMoney(long id,TopUpDto request) throws Exception {

        // Check data user from repository
        Optional<User> userOpt = userRepository.findById(id);

        // Validator
        userValidator.validateUserNotFound(userOpt);

        // Get user
        user = userOpt.get();

        // Check data balance from repository
        Optional<Balance> balanceOpt  = balanceRepository.findByUserId(user);

        // Conditional check
        if (balanceOpt.isPresent()) {

            // Get balance
            balance = balanceOpt.get();

            // Instance object
            topUp = new TopUp();

            // Set user
            topUp.setUserId(user);
            balance.setUserId(user);

            // Request
            topUp.setTopAmount(request.getAmount());
            balance.setMoney(request.getAmount() + balance.getMoney());

            // Save to database
            topUpRepository.save(topUp);
            balanceRepository.save(balance);

            // Response data
            responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Top Up success Updated", topUp.getTopAmount());

        }else {

            // Instance object
            topUp = new TopUp();
            balance = new Balance();

            // Set user
            topUp.setUserId(user);
            balance.setUserId(user);

            // Request
            topUp.setTopAmount(request.getAmount());
            balance.setMoney(request.getAmount());

            // Save to database
            topUpRepository.save(topUp);
            balanceRepository.save(balance);

            // Response data
            responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Top Up success Updated", topUp.getTopAmount());
        }
        return responseData;
    }

    // Transfer method
    @Override
    public ResponseData<Object> transfer(long id,TransferDto data) throws Exception {

        // Check data user from repository
        Optional<User> userOpt = userRepository.findById(id);

        // Validator
        userValidator.validateUserNotFound(userOpt);
        user = userOpt.get();

        // Check data balance from repository
        Optional<Balance> balanceOpt  = balanceRepository.findByUserId(user);

        // Validator
        balanceValidator.validateBalanceNotFound(balanceOpt);
        balance = balanceOpt.get();

        // Instance object
        transfer = new Transfer();

        // Set data
        transfer.setUser(user);
        transfer.setAmount(data.getAmount());
        transfer.setNotes(data.getNotes());
        
        // New attribute
        Long balancePast = balance.getMoney();

          // Validator
//        balanceValidator.validateBalanceEnough();

        // Conditional check
        if (balancePast < data.getAmount() || data.getAmount() < 10000) {

            // Response data
            responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "duit kurang/ minimal transfer 10000", null);
        }else {

            // Set sender balance
            balance.setMoney(balancePast - data.getAmount());
            balanceRepository.save(balance);

            // Validate User Receiver
            Optional<User> userOpt2 = userRepository.findByUsername(data.getUsername());
            userValidator.validateUserNotFound(userOpt2);
            user = userOpt2.get();

            // Validate Balance
            Optional<Balance> balanceReceiverOpt  = balanceRepository.findByUserId(user);
            balanceValidator.validateBalanceNotFound(balanceReceiverOpt);
            balanceReceiver = balanceReceiverOpt.get();

            // set id
            transfer.setUserReceiver(user);
            
            //set balance receiver
            balanceReceiver.setMoney(balanceReceiver.getMoney() + data.getAmount());

            // Save to database
            balanceRepository.save(balanceReceiver);
            transferRepository.save(transfer);

            // Response data
            responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Transfer success Updated", transfer.getAmount());
        }
        return responseData;
    }
    
}
