package net.backend.paylens.validator;

import net.backend.paylens.exception.costum.CustomBadRequestException;
import net.backend.paylens.exception.costum.CustomNotFoundException;
import net.backend.paylens.exception.costum.CustomUnauthorizedException;
import net.backend.paylens.model.entity.User;

import java.util.Optional;

public class UserValidator {

    // User not found method
    public void validateUserNotFound(Optional<User> userFind) throws Exception {
        if (userFind.isEmpty()) {
            throw new CustomNotFoundException("User is not found!");
        }
    }

    // User inactive method
    public void validateUserInactive(User user) throws Exception {
        if (user.getIsDeleted().equals(true)) {
            throw new Exception("User is inactive!");
        }
    }

    // Validate user found method
    public void validateUserFound(Optional<User> userFind) throws Exception {
        if (userFind.isPresent()) {
            throw new CustomBadRequestException("User is found!");
        }
    }

    // Validate wrong password method
    public void validateWrongPassword(String dbPassword, String dtoPassword) throws Exception {
        if (!dbPassword.equals(dtoPassword)) {
            throw new CustomUnauthorizedException("Unauthorized! Password does not match!");
        }
    }
}
