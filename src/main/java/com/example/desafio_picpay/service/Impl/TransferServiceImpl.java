package com.example.desafio_picpay.service.Impl;

import com.example.desafio_picpay.dto.TransferDto;
import com.example.desafio_picpay.dto.UserDto;
import com.example.desafio_picpay.entities.Transfer;
import com.example.desafio_picpay.entities.User;
import com.example.desafio_picpay.repository.TransferRepository;
import com.example.desafio_picpay.service.UserService;
import com.example.desafio_picpay.utils.EnumTipoUsuario;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransferServiceImpl {

    private TransferRepository transferRepository;

    private UserService userService;



    @Autowired
    public TransferServiceImpl(TransferRepository transferRepository, UserService userService) {
        this.transferRepository = transferRepository;
        this.userService = userService;
    }



    public TransferDto payment(Transfer transfer){
        String text = "";

        User pagador = UserDto.userDtoToUser(userService.findById(transfer.getIdPagador()));
        User recebedor = UserDto.userDtoToUser(userService.findById(transfer.getIdRecebedor()));

        if (pagador.getTipoUsuario().equals(EnumTipoUsuario.LOJISTA.name())){
            text = "Lojistas não podem fazer transferências";
        }else if (pagador.getSaldo().compareTo(BigDecimal.valueOf(transfer.getValor())) < 0){
            text = "Saldo insuficiente";
        }else{
            pagador.setSaldo(pagador.getSaldo().subtract(BigDecimal.valueOf(transfer.getValor())));
            recebedor.setSaldo(recebedor.getSaldo().add(BigDecimal.valueOf(transfer.getValor())));

        }
    }

    @Transactional
    public TransferDto findAll() {
        Transfer transfer = transferRepository.findAll().iterator().next();
        return TransferDto.builder()
                .idPagador(transfer.getIdPagador())
                .idRecebedor(transfer.getIdRecebedor())
                .valor(transfer.getValor())
                .build();
    }

    @Transactional
    public TransferDto findById(UUID id) {
        Transfer transfer = transferRepository.findById(id).orElseThrow();
        return TransferDto.builder()
                .idPagador(transfer.getIdPagador())
                .idRecebedor(transfer.getIdRecebedor())
                .valor(transfer.getValor())
                .build();
    }

    @Transactional
    public void deleteById(UUID id) {
        transferRepository.deleteById(id);
    }

}
