package net.backend.paylens.validator;

import java.util.Optional;

import org.springframework.stereotype.Service;

import net.backend.paylens.exception.custom.CustomNotFoundException;
import net.backend.paylens.model.entity.Balance;

@Service
public class BalanceValidator {
    // Balance not found method
    public void validateBalanceNotFound(Optional<Balance> userFind) throws Exception {
        if (userFind.isEmpty()) {
            throw new CustomNotFoundException("User is not found!");
        }
    }

    public void validateBalanceEnough(Optional<Balance> userFind) throws Exception {
        if (userFind.isEmpty()) {
            throw new CustomNotFoundException("duit kurang/ minimal transfer 10000");
        }
    }

}
