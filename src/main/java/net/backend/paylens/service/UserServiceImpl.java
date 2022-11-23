package net.backend.paylens.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import net.backend.paylens.model.dto.request.LoginDto;
import net.backend.paylens.model.dto.request.RegisterDto;
import net.backend.paylens.model.dto.response.ResponseData;
import net.backend.paylens.model.entity.DetailUser;
import net.backend.paylens.model.entity.User;
import net.backend.paylens.repository.DetailUserRepository;
import net.backend.paylens.repository.UserRepository;
import net.backend.paylens.validator.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    // Construct repository and validator
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DetailUserRepository detailUserRepository;
    @Autowired
    private UserValidator userValidator;

    // Attribute
    private User user;
    private DetailUser detailUser;
    private Map<Object, Object> data;
    private ResponseData<Object> responseData;

    // Register method
    @Override
    // DTO - Request : User input
    public ResponseData<Object> register(RegisterDto request) throws Exception {

        // check user apakah usernya ini udh terdaftar atau belum, check emailnya
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        // Validate if user is found
        userValidator.validateUserFound(userOpt);

        // instance object user
        user = new User(request.getEmail(), request.getPassword());

        // Save to database
        userRepository.save(user);

        // Instance object detail user
        detailUser = new DetailUser(request.getFirstName(), request.getLastName(), request.getPhoneNumber());

        // Set user and detail user
        detailUser.setUser(user);
        detailUserRepository.save(detailUser);

        // Spesific data what will send
        data = new HashMap<>();
        data.put("email", user.getEmail());
        data.put("firstName", detailUser.getFirstName());
        data.put("lastName", detailUser.getLastName());
        data.put("phoneNumber", detailUser.getPhoneNumber());

        // Response data
        responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Success registerd", data);
        return responseData;
    }

    // Login method
    @Override
    // DTO - Request : User input
    public ResponseData<Object> login(LoginDto request) throws Exception {

        // check user
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        // Validate user is not found
        userValidator.validateUserNotFound(userOpt);

        // user : db - model entity user
        user = userOpt.get();

        // validate wrong password
        userValidator.validateWrongPassword(user.getPassword(), request.getPassword());

        // data spesific
        data = new HashMap<>();
        data.put("email", user.getEmail());

        // response data
        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "Success login.", data);
        return responseData;
    }

    // Update user method
    @Override
    public ResponseData<Object> updateDetailUser(RegisterDto request) throws Exception {
        // cari user yg mau kita update detailnya
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        // validate user is not found
        userValidator.validateUserNotFound(userOpt);

        user = userOpt.get();

        // cari data detailnya
        Optional<DetailUser> detailOpt = detailUserRepository.findByUser(user);
        // cek ada atau tidak detailnya
        if (detailOpt.isPresent()) {
            detailUser = detailOpt.get();
            // update
            detailUser.setFirstName(request.getFirstName());
            detailUser.setLastName(request.getLastName());
            detailUser.setPhoneNumber(request.getPhoneNumber());
        } else {
            detailUser = new DetailUser(request.getFirstName(), request.getLastName(), request.getPhoneNumber());
            detailUser.setUser(user);
        }

        // save
        detailUserRepository.save(detailUser);

        // data
        data = new HashMap<>();
        data.put("firstName", detailUser.getFirstName());
        data.put("lastName", detailUser.getLastName());
        data.put("phoneNumber", detailUser.getPhoneNumber());

        // response data
        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "Success updated", data);
        return responseData;
    }

}
