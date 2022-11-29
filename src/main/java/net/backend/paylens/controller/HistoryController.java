package net.backend.paylens.controller;

import net.backend.paylens.model.dto.response.ResponseData;
import net.backend.paylens.service.HistoryService;
import net.backend.paylens.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
@CrossOrigin(value = "http://localhost:3000")
public class HistoryController {

    @Autowired
    private HistoryService historyService;
    private ResponseData<Object> responseData;

    // History transaction user controller
    @GetMapping("/{id}")
    public ResponseEntity<Object> addHistory(@PathVariable long id) throws Exception {
        responseData = historyService.addHistory(id);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }
}
