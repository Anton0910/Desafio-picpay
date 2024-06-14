package com.example.desafio_picpay.service;

import com.example.desafio_picpay.dto.TransferDto;
import com.example.desafio_picpay.entities.Transfer;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface TransferService {
    TransferDto payment(final TransferDto transfer);

    TransferDto findAll();

    TransferDto findById(UUID id);

    void deleteById(UUID id);
}
