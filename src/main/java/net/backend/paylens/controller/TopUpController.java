package net.backend.paylens.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.backend.paylens.model.dto.request.PinDto;
import net.backend.paylens.model.dto.request.TopUpDto;
import net.backend.paylens.model.dto.response.ResponseData;
import net.backend.paylens.service.TransactionService;

@RestController
@RequestMapping("/top_up")
@CrossOrigin(value = "http://localhost:3000")
public class TopUpController {
    @Autowired
    private TransactionService transactionService;

    private ResponseData<Object> responseData;

    @PostMapping("/{id}")
    public ResponseEntity<Object> topUp(@PathVariable long id,@RequestBody TopUpDto request) throws Exception{
        responseData = transactionService.topUpMoney(id ,request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }


}
