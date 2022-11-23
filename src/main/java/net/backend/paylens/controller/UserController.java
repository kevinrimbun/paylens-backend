package net.backend.paylens.controller;

//import javax.validation.Valid;

import net.backend.paylens.model.dto.request.LoginDto;
import net.backend.paylens.model.dto.request.RegisterDto;
import net.backend.paylens.model.dto.response.ResponseData;
import net.backend.paylens.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
    private ResponseData<Object> responseData;

    // Register controller
    @PostMapping
    public ResponseEntity<Object> signUp(@RequestBody RegisterDto request) throws Exception {
        responseData = userService.register(request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    // Login controller
    @PostMapping("/login")
    public ResponseEntity<Object> signIn(@RequestBody LoginDto request) throws Exception {
        responseData = userService.login(request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    // Update detail user controller
    @PutMapping
    public ResponseEntity<Object> updateDetailUser(@RequestBody RegisterDto request) throws Exception {
        responseData = userService.updateDetailUser(request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }
}
