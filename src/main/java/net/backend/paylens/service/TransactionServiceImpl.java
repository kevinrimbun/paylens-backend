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

    @Autowired
    private DetailUserRepository detailUserRepository;

    private TopUp topUp;
    private Balance balance;
    private Balance balanceReceiver;
    private Transfer transfer;
    private User user;
    private ResponseData<Object> responseData;
    private DetailUser detailUser;

    @Override
    public ResponseData<Object> topUpMoney(long id,TopUpDto request) throws Exception {

        Optional<User> userOpt = userRepository.findById(id);
        userValidator.validateUserNotFound(userOpt);
        //user
        user = userOpt.get();

        //Detail user
        Optional<DetailUser> detailUserOpt1 = detailUserRepository.findByUser(user);
        detailUser = detailUserOpt1.get();
        if (request.getPin().equals(detailUser.getPin())) {

            Optional<Balance> balanceOpt  = balanceRepository.findByUserId(user);
            if (balanceOpt.isPresent()) {
                balance = balanceOpt.get();

                topUp = new TopUp();

                //set user
                topUp.setUserId(user);
                balance.setUserId(user);

                //request
                topUp.setTopAmount(request.getAmount());
                balance.setMoney(request.getAmount() + balance.getMoney());

                //save
                topUpRepository.save(topUp);
                balanceRepository.save(balance);
                responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Top Up success Updated", topUp.getTopAmount());

            }else{
                topUp = new TopUp();
                balance = new Balance();

                //set user
                topUp.setUserId(user);
                balance.setUserId(user);

                //request
                topUp.setTopAmount(request.getAmount());
                balance.setMoney(request.getAmount());

                //save
                topUpRepository.save(topUp);
                balanceRepository.save(balance);
                responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Top Up success Updated", topUp.getTopAmount());
            }
        }else{
            responseData = new ResponseData<Object>(HttpStatus.UNAUTHORIZED.value(), "Pin salah", null);
        }
        return responseData;
    }

    @Override
    public ResponseData<Object> transfer(long id,TransferDto data) throws Exception {
        // Validate User
        Optional<User> userOpt = userRepository.findById(id);
        userValidator.validateUserNotFound(userOpt);
        user = userOpt.get();

        //Validate User Pin
        Optional<DetailUser> detailUserOpt1 = detailUserRepository.findByUser(user);
        detailUser = detailUserOpt1.get();
        if (data.getPin().equals(detailUser.getPin())) {
            //Validate balance
            Optional<Balance> balanceOpt  = balanceRepository.findByUserId(user);
            balanceValidator.validateBalanceNotFound(balanceOpt);
            balance = balanceOpt.get();

            //instance object
            transfer = new Transfer();

            //set data
            transfer.setUser(user);
            transfer.setAmount(data.getAmount());
            transfer.setNotes(data.getNotes());
            
            //new atribut
            Long balancePast = balance.getMoney();

            if (balancePast < data.getAmount() || data.getAmount() < 10000) {
                responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "duit kurang/ minimal transfer 10000", null);
            }else{
                //Set balance pengirim
                balance.setMoney(balancePast - data.getAmount());
                balanceRepository.save(balance);

                //Validate User Receiver
                Optional<User> userOpt2 = userRepository.findByUsername(data.getUsername());
                userValidator.validateUserNotFound(userOpt2);
                user = userOpt2.get();

                //Validate Balance
                Optional<Balance> balanceReceiverOpt  = balanceRepository.findByUserId(user);
                balanceValidator.validateBalanceNotFound(balanceReceiverOpt);
                balanceReceiver = balanceReceiverOpt.get();

                //set id
                transfer.setUserReceiver(user);
                
                //set balance receiver
                balanceReceiver.setMoney(balanceReceiver.getMoney() + data.getAmount());

                //Save
                balanceRepository.save(balanceReceiver);
                transferRepository.save(transfer);
                responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Transfer success Updated", transfer.getAmount());
            }
        }else{
            responseData = new ResponseData<Object>(HttpStatus.UNAUTHORIZED.value(), "Pin salah", topUp.getTopAmount());
        }
        return responseData;
    }
    
}
