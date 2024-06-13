package com.example.desafio_picpay.api;

import com.example.desafio_picpay.dto.TransferDto;
import com.example.desafio_picpay.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/payment")
    public ResponseEntity<TransferDto> saveTransfer(TransferDto transferDto) {
        transferService.saveTransfer(transferDto);
        return ResponseEntity.ok(transferDto);
    }
}
