package net.backend.paylens.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import net.backend.paylens.model.dto.request.LoginDto;
import net.backend.paylens.model.dto.request.PinDto;
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

        // Check the email has been registered or not
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        // Validate if user is found
        userValidator.validateUserFound(userOpt);

        // instance object user
        user = new User(request.getUsername(), request.getEmail(), request.getPassword());

        // Save to database
        userRepository.save(user);

        // Instance object detail user
        // detailUser = new DetailUser(request.getFirstName(), request.getLastName(), request.getPhoneNumber());

        // Set user and detail user
        // detailUser.setUser(user);
        // detailUserRepository.save(detailUser);

        // Spesific data what will send
        data = new HashMap<>();
        data.put("email", user.getEmail());
        // data.put("firstName", detailUser.getFirstName());
        // data.put("lastName", detailUser.getLastName());
        // data.put("phoneNumber", detailUser.getPhoneNumber());

        // Response data
        responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Register success!", data);
        return responseData;
    }

    // Login method
    @Override
    // DTO - Request : User input
    public ResponseData<Object> login(LoginDto request) throws Exception {

        // Check the email has been registered or not
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        // Validate user is not found
        userValidator.validateUserNotFound(userOpt);

        // User : Database - Model/Entity/User
        user = userOpt.get();

        // Validate wrong password
        userValidator.validateWrongPassword(user.getPassword(), request.getPassword());

        // Spesific data what will send
        data = new HashMap<>();
        data.put("email", user.getEmail());

        // Response data
        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "Login success!", data);
        return responseData;
    }

    // Update user method
    @Override
    public ResponseData<Object> updateDetailUser(RegisterDto request) throws Exception {

        // Check the email has been registered or not
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        // Validate user is not found
        userValidator.validateUserNotFound(userOpt);

        // User : Database - Model/Entity/User
        user = userOpt.get();

        // Looking for detailed data
        Optional<DetailUser> detailOpt = detailUserRepository.findByUser(user);

        // Check if there is or not the detailed data
        // if (detailOpt.isPresent()) {
        //     // Detail user : Database - Model/Entity/Detail user
        //     detailUser = detailOpt.get();
        //     // Update data
        //     detailUser.setFirstName(request.getFirstName());
        //     detailUser.setLastName(request.getLastName());
        //     detailUser.setPhoneNumber(request.getPhoneNumber());
        // } else {
        //     // Instance object detail user
        //     detailUser = new DetailUser(request.getFirstName(), request.getLastName(), request.getPhoneNumber());
        //     // Set detail user
        //     detailUser.setUser(user);
        // }

        // Save to database
        detailUserRepository.save(detailUser);

        // Spesific data what will send
        data = new HashMap<>();
        data.put("firstName", detailUser.getFirstName());
        data.put("lastName", detailUser.getLastName());
        data.put("phoneNumber", detailUser.getPhoneNumber());

        // Response data
        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "Update success!", data);
        return responseData;
    }

    @Override
    public ResponseData<Object> createPin(long id, PinDto request) throws Exception {
        // TODO Auto-generated method stub
        Optional<User> userOpt = userRepository.findById(id);
        userValidator.validateUserNotFound(userOpt);
        
        user = userOpt.get();
        detailUser = new DetailUser();
        detailUser.setUser(user);
    
        detailUser.setPin(request.getPin());
        detailUserRepository.save(detailUser);
        responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Create PIN success!", data);
        return responseData;
    }
}
