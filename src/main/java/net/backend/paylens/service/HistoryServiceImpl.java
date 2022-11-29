package net.backend.paylens.service;

import net.backend.paylens.model.dto.request.TopUpDto;
import net.backend.paylens.model.dto.request.TransferDto;
import net.backend.paylens.model.dto.response.ResponseData;
import net.backend.paylens.model.entity.*;
import net.backend.paylens.repository.*;
import net.backend.paylens.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class HistoryServiceImpl implements HistoryService {

    // Construct repository and validator
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private TopUpRepository topUpRepository;

    // Attribute
    private History history;
    private User user;
    private Map<Object, Object> data;
    private List<History> historyList;
    private ResponseData<Object> responseData;

    @Override
    public ResponseData<Object> addHistory(TransferDto requestTransferDto, TopUpDto requestTopUpDto, long id) {
        //        Optional<History> historyOpt = historyRepository.findByUserId(user);
        List<TopUp> topUpOpt = topUpRepository.findAll();
        List<Transfer> transferOpt = transferRepository.findAll();

        if (topUpOpt.isEmpty() && transferOpt.isEmpty()) {
            // Response data
            responseData = new ResponseData<Object>(HttpStatus.NOT_FOUND.value(), "History data is not found!", null);
        } else {

            // Instance object
            history = new History();
//            history = new History(requestTopUpDto.getUserId(), requestTopUpDto.getTopAmount(), requestTransferDto.getUsername(), requestTransferDto.getAmount());

            // Set data
            history.setTransfer(requestTransferDto.getUsername(), requestTransferDto.getAmount());
            history.setTopUp(requestTopUpDto.getAmount());

            // Save to database
            historyRepository.save(history);

            // Spesific data what will send
            data.put("userId", history.getUser());
            data.put("transfer", history.getTransfer());
            data.put("topUp", history.getTopUp());

            // Response data
            responseData = new ResponseData<Object>(HttpStatus.FOUND.value(), "History available!", data);
            return responseData;
        }
        return responseData;
    }

//    // Get all history data method
//    @Override
//    public ResponseData<Object> getAll(Boolean status) {
//
//        // Conditional check
//        if (status == null) {
//            // Get all history data
//            historyList = historyRepository.findAll();
//        } else {
//            historyList = historyRepository.findByIsDeleted(status);
//        }
//
//        // Response data
//        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "success", historyList);
//        return responseData;
//    }
//
//    // Get history data by id method
//    @Override
//    public ResponseData<Object> getHistoryByUserId(User id) {
//
////        Optional<History> historyOpt = historyRepository.findById(id.getId());
//        Optional<History> historyOpt = userRepository.findById(id);
//
//        // Conditional check
//        if (historyOpt.isPresent()) {
//            // History : Database - Model/Entity/History
//            history = historyOpt.get();
//            // Response data
//            responseData = new ResponseData<Object>(HttpStatus.OK.value(), "success", history);
//        } else {
//            // Response data
//            responseData = new ResponseData<Object>(HttpStatus.NOT_FOUND.value(), "empty data", null);
//        }
//        return responseData;
//    }
//
//    // Delete history data method
//    @Override
//    public ResponseData<Object> deleteHistory(long id) {
//
//        Optional<History> historyOpt = historyRepository.findById(id);
//
//        // Conditional check
//        if (historyOpt.isPresent()) {
//            // History : Database - Model/Entity/History
//            history = historyOpt.get();
//            // Set is deleted column = true
//            history.setIsDeleted(true);
//            // Save to database
//            historyRepository.save(history);
//            // Response data
//            responseData = new ResponseData<Object>(HttpStatus.OK.value(), "success", null);
//        } else {
//            // Response data
//            responseData = new ResponseData<Object>(HttpStatus.NOT_FOUND.value(), "empty data", null);
//        }
//        return responseData;
//    }
}
