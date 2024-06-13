package com.example.desafio_picpay.service;

import com.example.desafio_picpay.dto.TransferDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface TransferService {
    void saveTransfer(TransferDto transferDto);

    TransferDto findAll();

    TransferDto findById(UUID id);

    void deleteById(UUID id);
}
