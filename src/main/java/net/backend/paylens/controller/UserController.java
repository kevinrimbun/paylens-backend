package net.backend.paylens.controller;

import javax.validation.Valid;

import net.backend.paylens.model.dto.request.LoginDto;
import net.backend.paylens.model.dto.request.RegisterDto;
import net.backend.paylens.model.dto.response.ResponseData;
import net.backend.paylens.model.entity.User;
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
    @PostMapping
    public ResponseEntity<Object> signUp(@RequestBody @Valid RegisterDto request) throws Exception {
        responseData = userService.register(request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    // Login controller
    @PostMapping("/login")
    public ResponseEntity<Object> signIn(@RequestBody @Valid LoginDto request) throws Exception {
        responseData = userService.login(request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    // Update detail user controller
    @PutMapping
    public ResponseEntity<Object> updateDetailUser(@RequestBody @Valid RegisterDto request) throws Exception {
        responseData = userService.updateDetailUser(request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    // History transaction user controller
    @GetMapping("/history/{id}")
    public ResponseEntity<Object> getHistoryByUserId(@PathVariable User id) {
        responseData = historyService.getHistoryByUserId(id);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }
}