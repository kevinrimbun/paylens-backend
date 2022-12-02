package net.backend.paylens.controller;

import javax.validation.Valid;

import net.backend.paylens.model.dto.request.ChangePasswordDto;
import net.backend.paylens.model.dto.request.LoginDto;
import net.backend.paylens.model.dto.request.MailDto;
import net.backend.paylens.model.dto.request.PhoneNumberDto;
import net.backend.paylens.model.dto.request.PinDto;
import net.backend.paylens.model.dto.request.RegisterDto;
import net.backend.paylens.model.dto.response.ResponseData;
import net.backend.paylens.service.MailService;
import net.backend.paylens.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {

    // Construct service and response data
    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    private ResponseData<Object> responseData;

    // Register controller
    @PostMapping("/register")
    public ResponseEntity<Object> signUp(@RequestBody @Valid RegisterDto request) throws Exception {
        responseData = userService.register(request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @PostMapping("/register/pin/{id}")
    public ResponseEntity<Object> createPin(@PathVariable long id,  @RequestBody @Valid PinDto request) throws Exception {
        responseData = userService.createPin(id, request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    // Login controller
    @PostMapping("/login")
    public ResponseEntity<Object> signIn(@RequestBody @Valid LoginDto request) throws Exception {
        responseData = userService.login(request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    // Detail Users Controller
    @PostMapping("/phone-number/{id}")
    public ResponseEntity<Object> phoneNumber(@PathVariable long id,  @RequestBody @Valid PhoneNumberDto request) throws Exception {
        responseData = userService.phoneNumber(id, request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @DeleteMapping("/phone-number/delete/{id}")
    public ResponseEntity<Object> deletePhoneNumber(@PathVariable long id) throws Exception {
      responseData = userService.deletePhoneNumber(id);
      return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    // Update detail user controller
    @PutMapping
    public ResponseEntity<Object> updateDetailUser(@RequestBody @Valid RegisterDto request) throws Exception {
        responseData = userService.updateDetailUser(request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @PutMapping("/change-password/{id}")
    public ResponseEntity<Object> changePassword(@PathVariable long id, @RequestBody @Valid ChangePasswordDto request) throws Exception {
        responseData = userService.changePassword(id, request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @PostMapping("/email")
    public ResponseEntity<Object> sendMail(@RequestBody MailDto request) throws Exception {
        mailService.sendMail(request);
        return ResponseEntity.ok().body("Email sent successfully!");
    }

    @PutMapping("/forgot-password/{id}")
    public ResponseEntity<Object> forgotPassword(@PathVariable long id, @RequestBody @Valid ChangePasswordDto request) throws Exception {
        responseData = userService.forgotPassword(id, request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }
}