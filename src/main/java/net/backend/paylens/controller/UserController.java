package net.backend.paylens.controller;

import javax.validation.Valid;

import net.backend.paylens.model.dto.request.*;
import net.backend.paylens.model.dto.response.ResponseData;
import net.backend.paylens.model.entity.Transfer;
import net.backend.paylens.service.HistoryService;
import net.backend.paylens.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
public class UserController {

    // Construct service and response data
    @Autowired
    private UserService userService;
    @Autowired
    private HistoryService historyService;
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

    // Delete phone number controller
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

    // Change password controller
    @PutMapping("/change-password/{id}")
    public ResponseEntity<Object> changePassword(@PathVariable long id, @RequestBody @Valid ChangePasswordDto request) throws Exception {
        responseData = userService.changePassword(id, request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    // History transaction user controller
    @GetMapping("/history/{id}")
    public ResponseEntity<Object> addHistory(@PathVariable long id, TopUpDto requestTopUpDto, TransferDto requestTransferDto) throws Exception {
//        responseData = historyService.getHistoryByUserId(id);
        responseData = historyService.addHistory(requestTransferDto, requestTopUpDto, id);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }
}