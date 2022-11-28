package net.backend.paylens.service;

import net.backend.paylens.model.dto.request.LoginDto;
import net.backend.paylens.model.dto.request.RegisterDto;
import net.backend.paylens.model.dto.response.ResponseData;
import net.backend.paylens.model.entity.User;

public interface HistoryService {

    // Add history from user transaction
//    ResponseData<Object> addHistory(TransferDto requestDto);

    // Get all history data
    ResponseData<Object> getAll(Boolean status);
    ResponseData<Object> getHistoryByUserId(User id);

    // Delete history data
    ResponseData<Object> deleteHistory(long id);

}
