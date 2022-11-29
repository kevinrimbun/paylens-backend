package net.backend.paylens.service;

import net.backend.paylens.model.dto.request.TopUpDto;
import net.backend.paylens.model.dto.request.TransferDto;
import net.backend.paylens.model.dto.response.ResponseData;
import net.backend.paylens.model.entity.TopUp;
import net.backend.paylens.model.entity.Transfer;

public interface HistoryService {

//     Add history from user transaction
    ResponseData<Object> addHistory(TransferDto requestTransferDto, TopUpDto requestTopUpDto, long id);

//    // Get all history data
//    ResponseData<Object> getAll(Boolean status);
//    // Get history data by user id
//    ResponseData<Object> getHistoryByUserId(User id);
//    // Delete history data
//    ResponseData<Object> deleteHistory(long id);

}
