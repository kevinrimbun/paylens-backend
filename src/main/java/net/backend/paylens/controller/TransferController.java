package net.backend.paylens.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.backend.paylens.model.dto.request.TransferDto;
import net.backend.paylens.model.dto.response.ResponseData;
import net.backend.paylens.service.TransactionService;
@RestController
@RequestMapping("/transfer")
@CrossOrigin(value = "https://paylens.vercel.app")
public class TransferController {
    @Autowired
    private TransactionService transactionService;

    private ResponseData<Object> responseData;

    @PostMapping("/{id}")
    public ResponseEntity<Object> transfer(@PathVariable long id,@RequestBody TransferDto request) throws Exception{
        responseData = transactionService.transfer(id,request);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }
}
