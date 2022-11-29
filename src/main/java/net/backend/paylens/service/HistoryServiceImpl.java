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
import java.util.Optional;

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
    private User user;
    private Map<Object, Object> data;
    private List<History> historyList;
    private ResponseData<Object> responseData;

    @Override
    public ResponseData<Object> addHistory(long id) {

        // Search data user by id
        user = userRepository.findById(id).get();

        // Check from history repository is available user
        Optional<List<History>> historyOpt = historyRepository.findByUser(user);

        // Conditional check
        if (historyOpt.isEmpty()) {

            // Response data
            responseData = new ResponseData<Object>(HttpStatus.NOT_FOUND.value(), "History data is not found!", null);
        } else {

            // Get all data
            historyList = historyOpt.get();

            // Response data
            responseData = new ResponseData<Object>(HttpStatus.FOUND.value(), "History available!", historyList);
        }
        return responseData;
    }
}
