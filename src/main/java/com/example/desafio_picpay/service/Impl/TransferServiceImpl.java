package com.example.desafio_picpay.service.Impl;

import com.example.desafio_picpay.config.TransferMapper;
import com.example.desafio_picpay.config.UserMapper;
import com.example.desafio_picpay.dto.TransferDto;
import com.example.desafio_picpay.dto.UserDto;
import com.example.desafio_picpay.entities.Transfer;
import com.example.desafio_picpay.entities.User;
import com.example.desafio_picpay.itg.AuthorizationTransfer;
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

    private final TransferRepository transferRepository;

    private final UserService userService;

    private final AuthorizationTransfer authorizationTransfer;




    @Autowired
    public TransferServiceImpl(final TransferRepository transferRepository,
                               final UserService userService, final AuthorizationTransfer authorizationTransfer) {
        this.transferRepository = transferRepository;
        this.userService = userService;
        this.authorizationTransfer = authorizationTransfer;
    }



    public TransferDto payment(final TransferDto transferDto) {
        String text = "Erro na transação";

        User pagador = UserDto.userDtoToUser(userService.findById(transferDto.getIdPagador()));
        User recebedor = UserDto.userDtoToUser(userService.findById(transferDto.getIdRecebedor()));

        if (pagador.getTipoUsuario().equals(EnumTipoUsuario.LOJISTA.name())) {
            text = "Lojistas não podem fazer transferências";
        } else if (pagador.getSaldo().compareTo(BigDecimal.valueOf(transferDto.getValor())) < 0) {
            text = "Saldo insuficiente";
        } else {
            pagador.setSaldo(pagador.getSaldo().subtract(BigDecimal.valueOf(transferDto.getValor())));
            recebedor.setSaldo(recebedor.getSaldo().add(BigDecimal.valueOf(transferDto.getValor())));
            if (authorizationTransfer.authorizationTransfer()) {
                userService.saveUser(UserMapper.INSTANCE.userToUserDto(pagador));
                userService.saveUser(UserMapper.INSTANCE.userToUserDto(recebedor));
                transferRepository.save(TransferMapper.INSTANCE.transferDtoToTransfer(transferDto));
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
