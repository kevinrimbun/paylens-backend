package net.backend.paylens.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import net.backend.paylens.model.entity.*;
import net.backend.paylens.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.backend.paylens.model.dto.request.PinDto;
import net.backend.paylens.model.dto.request.TopUpDto;
import net.backend.paylens.model.dto.request.TransferDto;
import net.backend.paylens.model.dto.response.ResponseData;
import net.backend.paylens.model.entity.Balance;
import net.backend.paylens.model.entity.DetailUser;
import net.backend.paylens.model.entity.TopUp;
import net.backend.paylens.model.entity.Transfer;
import net.backend.paylens.model.entity.User;
import net.backend.paylens.repository.BalanceRepository;
import net.backend.paylens.repository.DetailUserRepository;
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
    private HistoryRepository historyRepository;
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
    @Autowired
    private DetailUserRepository detailUserRepository;

    // Attribute
    private History history;
    private User user;

    private DetailUser detailUser;
    private TopUp topUp;
    private Transfer transfer;
    private Balance balance;
    private Balance balanceReceiver;
    private Map<Object, Object> data;
    private ResponseData<Object> responseData;

    //  Top up method
    @Override
    public ResponseData<Object> topUpMoney(long id,TopUpDto request) throws Exception {

        // Check data user from repository
        Optional<User> userOpt = userRepository.findById(id);

        // Instance object
        history = new History();

        // Validator
        userValidator.validateUserNotFound(userOpt);

        // Get user
        user = userOpt.get();

        //Detail user
        Optional<DetailUser> detailUserOpt1 = detailUserRepository.findByUser(user);

        // validator
        userValidator.validateDetailUserNotFound(detailUserOpt1);

        // Get detail user
        detailUser = detailUserOpt1.get();

        // Conditional check
        if (request.getPin().equals(detailUser.getPin())) {

            // Check data balance from repository
            Optional<Balance> balanceOpt = balanceRepository.findByUserId(user);

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

                // Set history
                history.setTopUp(topUp);
                history.setUser(user);
                history.setTransfer(null);

                // Save to database
                historyRepository.save(history);

                // data = new HashMap<>();
                // data.put("detailUserId", detailUser.getId());
                // data.put("userId", user.getId());
                // data.put("fileId", fileUpload.getId());
                // data.put("username", user.getUsername());

                // Response data
                responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Top Up success Updated", topUp.getTopAmount());

            } else {

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

                // Set history
                history.setTopUp(topUp);
                history.setUser(user);
                history.setTransfer(null);

                // Save to database
                historyRepository.save(history);

                // Response data
                responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Top Up success Updated", topUp.getTopAmount());
            }
        }else{
            responseData = new ResponseData<Object>(HttpStatus.UNAUTHORIZED.value(), "Pin salah", null);
        }
        return responseData;
    }

    // Transfer method
    @Override
    public ResponseData<Object> transfer(long id, TransferDto data) throws Exception {

        // Check data user from repository
        Optional<User> userOpt = userRepository.findById(id);

        // Instance object
        history = new History();

        // Validator
        userValidator.validateUserNotFound(userOpt);
        user = userOpt.get();

        // Check data balance from repository
        Optional<Balance> balanceOpt  = balanceRepository.findByUserId(user);

        // Validator
        balanceValidator.validateBalanceNotFound(balanceOpt);
        balance = balanceOpt.get();

        //Validate User Pin
        Optional<DetailUser> detailUserOpt1 = detailUserRepository.findByUser(user);
        detailUser = detailUserOpt1.get();
        if (data.getPin().equals(detailUser.getPin())) {

            //Validate balance
            // Optional<Balance> balanceOpt3  = balanceRepository.findByUserId(user);
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


            // Conditional check
            if (balancePast < data.getAmount() || data.getAmount() < 10000) {

                // Response data
                responseData = new ResponseData<Object>(HttpStatus.EXPECTATION_FAILED.value(), "duit kurang/ minimal transfer 10000", null);
            }else {

                // Set sender balance
                balance.setMoney(balancePast - data.getAmount());
                balanceRepository.save(balance);

                // Validate User Receiver
                Optional<User> userOpt2 = userRepository.findByUsername(data.getUsername());
                userValidator.validateUserNotFound(userOpt2);

                // Instance object
                User user02 = userOpt2.get();

                // Validate Balance
                Optional<Balance> balanceReceiverOpt  = balanceRepository.findByUserId(user02);
                balanceValidator.validateBalanceNotFound(balanceReceiverOpt);
                balanceReceiver = balanceReceiverOpt.get();

                // set id
                transfer.setUserReceiver(user02);

                //set balance receiver
                balanceReceiver.setMoney(balanceReceiver.getMoney() + data.getAmount());

                // Save to database
                balanceRepository.save(balanceReceiver);
                transferRepository.save(transfer);

                // Set history
                history.setTransfer(transfer);
                history.setUser(user);
                history.setTopUp(null);

                // Save to database
                historyRepository.save(history);

                // Response data
                responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Transfer success Updated", transfer.getAmount());
                }

            }else{
                responseData = new ResponseData<Object>(HttpStatus.UNAUTHORIZED.value(), "Pin salah", null);
            }
        return responseData;
    }

}
