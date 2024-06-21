package com.example.desafio_picpay.service.Impl;

import com.example.desafio_picpay.dto.TransferDto;
import com.example.desafio_picpay.dto.UserDto;
import com.example.desafio_picpay.entities.Transfer;
import com.example.desafio_picpay.entities.User;
import com.example.desafio_picpay.itg.AuthorizationTransfer;
import com.example.desafio_picpay.repository.TransferRepository;
import com.example.desafio_picpay.repository.UserRepository;
import com.example.desafio_picpay.service.TransferService;
import com.example.desafio_picpay.service.UserService;
import com.example.desafio_picpay.utils.EnumTipoUsuario;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final UserService userService;
    private final AuthorizationTransfer authorizationTransfer;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public TransferServiceImpl(final TransferRepository transferRepository,
                               final UserService userService,
                               final AuthorizationTransfer authorizationTransfer,
                               final ModelMapper modelMapper, UserRepository userRepository) {
        this.transferRepository = transferRepository;
        this.userService = userService;
        this.authorizationTransfer = authorizationTransfer;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }



    public TransferDto payment(TransferDto transferDto) {
        String text = "Erro na transação";
        User pagador;
        User recebedor;
        try {
            pagador =  userRepository.findByEmail(transferDto.getEmailPagador());
            recebedor = userRepository.findByEmail(transferDto.getEmailRecebedor());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        if (pagador.getTipoUsuario().equals(EnumTipoUsuario.LOJISTA.name())) {
            text = "Lojistas não podem fazer transferências";
        } else if (pagador.getSaldo().compareTo(BigDecimal.valueOf(transferDto.getValor())) <= 0) {
            text = "Saldo insuficiente";
        } else {
            pagador.setSaldo(pagador.getSaldo().subtract(BigDecimal.valueOf(transferDto.getValor())));
            recebedor.setSaldo(recebedor.getSaldo().add(BigDecimal.valueOf(transferDto.getValor())));

            boolean authorization = authorizationTransfer.authorizationTransfer();
            if (authorization) {

                userRepository.save(pagador);
                userRepository.save(recebedor);
                transferRepository.save(modelMapper.map(transferDto, Transfer.class));
                text = "Transferência realizada com sucesso";

                transferDto.setText(text);
                transferDto.setSaldoPagador(pagador.getSaldo().doubleValue());
                transferDto.setSaldoRecebedor(recebedor.getSaldo().doubleValue());
                return transferDto;
            }
        }

        return new TransferDto(text);
    }



    @Transactional
    public TransferDto findAll() {
        Transfer transfer = transferRepository.findAll().iterator().next();
        return modelMapper.map(transfer, TransferDto.class);
    }

    @Transactional
    public TransferDto findById(UUID id) {
        Transfer transfer = transferRepository.findById(id).orElseThrow();
        return modelMapper.map(transfer, TransferDto.class);
    }

    @Transactional
    public void deleteById(UUID id) {
        transferRepository.deleteById(id);
    }

}
