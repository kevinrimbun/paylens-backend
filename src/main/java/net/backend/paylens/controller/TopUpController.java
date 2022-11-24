package net.backend.paylens.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.backend.paylens.model.dto.request.TopUpDto;
import net.backend.paylens.model.dto.response.ResponseData;
import net.backend.paylens.services.TransactionService;

@RestController
@RequestMapping("/top_up")
@CrossOrigin(value = "http://localhost:3000")
public class TopUpController {
    @Autowired
    private TransactionService transactionService;

    private ResponseData responseData;

    @PostMapping
    public ResponseEntity<Object> topUp(@RequestBody TopUpDto request) throws Exception{
        responseData = transactionService.topUpMoney(request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @PutMapping
    public ResponseEntity<Object> updateBalance(@RequestBody TopUpDto request) throws Exception{
        responseData = transactionService.updateBalance(request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }
}
