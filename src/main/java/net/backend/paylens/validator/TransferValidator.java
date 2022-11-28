package net.backend.paylens.validator;

import net.backend.paylens.exception.custom.CustomBadRequestException;
import net.backend.paylens.exception.custom.CustomNotFoundException;
import net.backend.paylens.model.entity.Transfer;
import net.backend.paylens.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferValidator {

    // Validate receiver method
    public void validateReceiver(User sender, User receiver) throws Exception {
        if (sender == receiver) {
            throw new CustomBadRequestException("Unable to make a transaction, please choose the receiver correctly!");
        }
    }

    // Validate minimum transfer method
    public void validateMinimumTransfer(Long amountRequest) throws Exception {
        if (amountRequest < 25000) {
            throw new CustomBadRequestException("Cannot make transactions, the minimum transfer amount is Rp 25000!");
        }
    }
}
