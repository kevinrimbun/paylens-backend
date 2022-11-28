package net.backend.paylens.validator;

import net.backend.paylens.exception.custom.CustomBadRequestException;
import net.backend.paylens.exception.custom.CustomNotFoundException;
import net.backend.paylens.exception.custom.CustomUnauthorizedException;
import net.backend.paylens.model.entity.DetailUser;
import net.backend.paylens.model.entity.TopUp;
import net.backend.paylens.model.entity.Transfer;
import net.backend.paylens.model.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class UserValidator {

    // Validate user not found method
    public void validateUserNotFound(Optional<User> userFind) throws Exception {
        if (userFind.isEmpty()) {
            throw new CustomNotFoundException("User is not found! Please register!");
        }
    }

    // Validate detail user not found method
    public void validateDetailUserNotFound(Optional<DetailUser> detailUserFind) throws Exception {
        if (detailUserFind.isEmpty()) {
            throw new CustomNotFoundException("Detail User is not found!");
        }
    }

    // Validate user inactive method
    public void validateUserInactive(User user) throws Exception {
        if (user.getIsDeleted().equals(true)) {
            throw new Exception("User is inactive!");
        }
    }

    // Validate user found method
    public void validateUserFound(Optional<User> userFind) throws Exception {
        if (userFind.isPresent()) {
            throw new CustomBadRequestException("User is found! Please Login!");
        }
    }

    // Validate username found method
    public void validateUserNameFound(Optional<User> userNameOpt) throws Exception {
        if (userNameOpt.isPresent()) {
            throw new CustomNotFoundException("Username already exist. Please login!");
        }
    }

    // Validate email found method
    public void validateEmailFound(Optional<User> emailOpt) throws Exception {
        if (emailOpt.isPresent()) {
            throw new CustomNotFoundException("Email already exist. Please login!");
        }
    }

    // Validate wrong password method
    public void validateWrongPassword(String dbPassword, String dtoPassword) throws Exception {
        if (!dbPassword.equals(dtoPassword)) {
            throw new CustomUnauthorizedException("Unauthorized! Password does not match!");
        }
    }

//    // Validate same PIN method
//    public void validateSamePin(Optional<Pin> pinOptional) throws Exception {
//        if (pinOptional.isPresent()) {
//            throw new CustomNotFoundException("The PIN cannot be the same as the previous one!");
//        }
//    }

    // Validate history method
    public void validateNoTransactions(List<Transfer> transferList, List<TopUp> topUpList) throws Exception {
        if (transferList.isEmpty() && topUpList.isEmpty()) {
            throw new CustomNotFoundException("Transaction data is not found!");
        }
    }
}