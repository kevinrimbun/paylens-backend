package net.backend.paylens.validator;

import java.util.Optional;

import net.backend.paylens.exception.custom.CustomBadRequestException;
import org.springframework.stereotype.Service;

import net.backend.paylens.exception.custom.CustomNotFoundException;
import net.backend.paylens.model.entity.Balance;

@Service
public class BalanceValidator {


    // Balance not found method
    public void validateBalanceNotFound(Optional<Balance> userFind) throws Exception {
        if (userFind.isEmpty()) {
            throw new CustomNotFoundException("Balance is not found!");
        }
    }

    // Validate balance is not enough method
    public void validateBalanceEnough(Optional<Balance> userFind) throws Exception {
        if (userFind.isEmpty()) {
            throw new CustomNotFoundException("\"Balance is not enough. Your transaction cannot be processed!");
        }
    }

//    // Validate balance method
//    public void validateBalance( Long amountSender, Long amountRequest) throws Exception {
//        if (amountRequest > amountSender) {
//            throw new CustomBadRequestException("Balance is not enough. Your transaction cannot be processed!");
//        }
//    }

}
