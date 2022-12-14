package net.backend.paylens.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import net.backend.paylens.config.jwt.JwtUtil;
import net.backend.paylens.model.dto.request.ChangePasswordDto;
import net.backend.paylens.model.dto.request.ForgotPasswordDto;
import net.backend.paylens.model.dto.request.LoginDto;
import net.backend.paylens.model.dto.request.PhoneNumberDto;
import net.backend.paylens.model.dto.request.PinDto;
import net.backend.paylens.model.dto.request.RegisterDto;
import net.backend.paylens.model.dto.response.ResponseData;
import net.backend.paylens.model.entity.Balance;
import net.backend.paylens.model.entity.DetailUser;
import net.backend.paylens.model.entity.ERole;
import net.backend.paylens.model.entity.FileUpload;
import net.backend.paylens.model.entity.Role;
import net.backend.paylens.model.entity.User;
import net.backend.paylens.model.entity.UserRole;
import net.backend.paylens.repository.BalanceRepository;
import net.backend.paylens.repository.DetailUserRepository;
import net.backend.paylens.repository.FileRepository;
import net.backend.paylens.repository.RoleRepository;
import net.backend.paylens.repository.UserRepository;
import net.backend.paylens.repository.UserRoleRepository;
import net.backend.paylens.validator.UserValidator;
import org.springframework.http.HttpStatus;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private BalanceRepository balanceRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    // Attribute
    private User user;
    private DetailUser detailUser;
    private FileUpload fileUpload;
    private Map<Object, Object> data;
    private ResponseData<Object> responseData;
    private Balance balance;

    @Autowired
    private JavaMailSender javaMailSender;

    // Register
    @Override
    // DTO - Request : User input
    public ResponseData<Object> register(RegisterDto request) throws Exception {

        // Check the email has been registered or not
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        // Validate if user is found
        userValidator.validateUserFound(userOpt);

        // instance object user
        user = new User(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()));

        // FName & LName for Detail User
        detailUser = new DetailUser();
        int spacePosition = request.getUsername().indexOf(" ");

        // Set data
        detailUser.setUser(user);
        detailUser.setFirstName(request.getUsername().substring(0, spacePosition));
        detailUser.setLastName(request.getUsername().substring(spacePosition + 1));

        // Save to database
        userRepository.save(user);
        detailUserRepository.save(detailUser);

        // User Role
        UserRole userRole = new UserRole();
        Role role = new Role();
        if (request.getRole() == null) {
            role = roleRepository.findByRoleName(ERole.USER);
        } else if (ERole.SUPER_ADMIN.name().equalsIgnoreCase(request.getRole())) {
            role = roleRepository.findByRoleName(ERole.SUPER_ADMIN);
        } else if (ERole.SUPER_USER.name().equalsIgnoreCase(request.getRole())) {
            role = roleRepository.findByRoleName(ERole.SUPER_USER);
        }

        userRole.setRole(role);
        userRole.setUser(user);
        userRoleRepository.save(userRole);

// File Upload for Relation with userId
        FileUpload fileUpload = new FileUpload();
        fileUpload.setUser(user);
        fileUpload.setData(null);
        fileRepository.save(fileUpload);


        Optional<Balance> balanceOpt  = balanceRepository.findByUserId(user);
        if (balanceOpt.isEmpty()) {
            balance = new Balance();
            balance.setMoney(0L);
            balance.setUserId(user);
            balanceRepository.save(balance);
        }

        // Spesific data what will send
        data = new HashMap<>();
        data.put("detailUserId", detailUser.getId());
        data.put("userId", user.getId());
        data.put("fileId", fileUpload.getId());
        data.put("username", user.getUsername());
        data.put("email", user.getEmail());
        data.put("role", role);

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
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // generate token
        String jwtToken = jwtUtil.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Validate user is not found
        userValidator.validateUserNotFound(userOpt);

        // User : Database - Model/Entity/User
        user = userOpt.get();

        detailUser = new DetailUser();
        Optional<Balance> balanceOpt  = balanceRepository.findByUserId(user);
        data = new HashMap<>();

        if (balanceOpt.isPresent()) {
            balance = balanceOpt.get();
            data.put("balance", balance.getMoney());
        }
        // detailUser = new DetailUser();
        Optional<DetailUser> detailUserOpt = detailUserRepository.findByUser(user);
        detailUser = detailUserOpt.get();
        Optional<FileUpload> fileUploadOpt = fileRepository.findByUser(user);
        fileUpload = fileUploadOpt.get();

        if (detailUserOpt.isPresent()) {
            detailUser = detailUserOpt.get();
            data.put("detailUserId", detailUser.getId());
        }

        // Spesific data what will send
        data.put("fileId", fileUpload.getId());
        data.put("userId", user.getId());
        data.put("token", jwtToken);
        data.put("username", user.getUsername());
        data.put("email", userDetails.getUsername());

        // Response data
        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "Login success!", data);
        return responseData;
    }

    // Create PIN method
    @Override
    public ResponseData<Object> createPin(long id, PinDto request) throws Exception {

        // Check the detail user is already exist or not
        Optional<DetailUser> detailUserOpt = detailUserRepository.findById(id);

        // Conditional check
        if (detailUserOpt.isPresent()) {

            // Get detail user
            detailUser = detailUserOpt.get();
            detailUser.setPin(request.getPin());

            // Spesific data what will send
            data = new HashMap<>();
            data.put("detailUserId", detailUser.getId());

            // Save to database
            detailUserRepository.save(detailUser);
    
            // Response data
            responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Create PIN Success", data);
        } else {
            // Response data
            responseData = new ResponseData<Object>(HttpStatus.NOT_FOUND.value(), "Detail User Not Found", null);
        }
        return responseData;
    }

    // CHange password method
    @Override
    public ResponseData<Object> changePassword(long id, ChangePasswordDto request) throws Exception {

        // Check the user has been registered/login or not
        Optional<User> userOpt = userRepository.findById(id);

        // Validate if user not found
        userValidator.validateUserNotFound(userOpt);
        user = userOpt.get();
        userValidator.validateWrongPassword(user.getPassword(), request.getOldPassword());

        
        // Get User
        user.setPassword(passwordEncoder.encode(request.getNewPassword()) );

        // Save to database
        userRepository.save(user);

        // Spesific data what will send
        data = new HashMap<>();
        data.put("userId", user.getId());
        data.put("username", user.getUsername());
        data.put("email", user.getEmail());

        // Response data
        responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Change Password success!", data);
        return responseData;
    }

    // Add phone number method
    @Override
    public ResponseData<Object> phoneNumber(long id, PhoneNumberDto request) throws Exception {

        // Check the detail user is found or not
        Optional<DetailUser> detailUserOpt = detailUserRepository.findById(id);

        // Conditional check
        if (detailUserOpt.isPresent()) {

            // Get detail user
            detailUser = detailUserOpt.get();
            detailUser.setPhoneNumber(request.getPhoneNumber());

            // Spesific data what will send
            data = new HashMap<>();
            data.put("detailUserId", detailUser.getId());

            // Save to database
            detailUserRepository.save(detailUser);

            // Response data
            responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Create Phone Number success!",data);
        } else {
            // Response data
            responseData = new ResponseData<Object>(HttpStatus.NOT_FOUND.value(), "Detail User Not Found",null);
        }
        return responseData;
    }

    // Delete phone number method
    @Override
    public ResponseData<Object> deletePhoneNumber(long id) throws Exception {

        // Check the detail user is found or not
        Optional<DetailUser> detailUserOpt = detailUserRepository.findById(id);

        // Conditional check
        if (detailUserOpt.isPresent()) {

          // Get detail user
          detailUser = detailUserOpt.get();
          detailUser.setPhoneNumber(null);
    
          data = new HashMap<>();
          data.put("detailUserId", detailUser.getId());

          // Save to database
          detailUserRepository.save(detailUser);
    
          // Response data
          responseData = new ResponseData<Object>(HttpStatus.OK.value(), "Delete Phone Number Success", data);
        } else {
          // Response data
          responseData = new ResponseData<Object>(HttpStatus.NOT_FOUND.value(), "Detail User Not Found", null);
        }
        return responseData;
    }

    @Override
    public ResponseData<Object> forgotPassword(long id, ForgotPasswordDto request) throws Exception {
        // TODO Auto-generated method stub
        Optional<User> userOpt = userRepository.findById(id);

        // Validate if user not found
        userValidator.validateUserNotFound(userOpt);

        // Get User
        user = userOpt.get();
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        data = new HashMap<>();
        data.put("userId", user.getId());
        data.put("username", user.getUsername());
        data.put("email", user.getEmail());

        responseData = new ResponseData<Object>(HttpStatus.CREATED.value(), "Change Password success!", data);
        return responseData;
    }

    @Override
    public ResponseData<Object> getById(long id) {
        // TODO Auto-generated method stub
        Optional<DetailUser> detailUserOpt = detailUserRepository.findById(id);
        if ( detailUserOpt.isPresent()) {
            detailUser = detailUserOpt.get();
            responseData = new ResponseData<Object>(HttpStatus.OK.value(), "success", detailUser);
        } else {
            responseData = new ResponseData<Object>(HttpStatus.NOT_FOUND.value(), "empty data", null);
        }
        return responseData;

    }

}
