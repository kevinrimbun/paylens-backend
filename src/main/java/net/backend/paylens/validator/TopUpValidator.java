package net.backend.paylens.validator;

import net.backend.paylens.exception.custom.CustomNotFoundException;
import net.backend.paylens.model.entity.TopUp;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopUpValidator {

    // Validate top up found method
    public void validateTopUpNotFound( Optional<TopUp> topUpOptional) throws Exception {
        if (topUpOptional.isEmpty()) {
            throw new CustomNotFoundException("Transaction is not found!");
        }
    }

    // Validate payment found method
    public void validatePaymentNameFound(Optional<TopUp> topUpOptional) throws Exception {
        if (topUpOptional.isPresent()) {
            throw new CustomNotFoundException("Transaction data already exists!");
        }
    }
}
