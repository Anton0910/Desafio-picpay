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
    private final String TRANSFERENCIA_NEGADA = "Transferência não autorizada";
    private final String SALDO_INSUFICIENTE = "Saldo insuficiente";
    private final String LOJISTA_NEGADO = "Lojista não pode realizar transferência";
    private final String TRANSFERENCIA_REALIZADA = "Transferência realizada com sucesso";

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
        User pagador;
        User recebedor;

        try {
            pagador =  userRepository.findByEmail(transferDto.getEmailPagador());
            recebedor = userRepository.findByEmail(transferDto.getEmailRecebedor());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

       if (validateType(pagador)) {
            return new TransferDto(LOJISTA_NEGADO);
        }
       else if (validateSaldo(pagador, transferDto)) {
            return new TransferDto(SALDO_INSUFICIENTE);
        }
       else {
           return makeTransfer(pagador, recebedor, transferDto);
       }

    }

    public boolean validateType(User user) {
        return user.getTipoUsuario().equals(EnumTipoUsuario.LOJISTA.name());
    }

    public boolean validateSaldo(User user, TransferDto transferDto) {
        return user.getSaldo().compareTo(BigDecimal.valueOf(transferDto.getValor())) <= 0;
    }

    public TransferDto makeTransfer(User pagador, User recebedor, TransferDto transferDto) {
        pagador.setSaldo(pagador.getSaldo().subtract(BigDecimal.valueOf(transferDto.getValor())));
        recebedor.setSaldo(recebedor.getSaldo().add(BigDecimal.valueOf(transferDto.getValor())));

        boolean authorization = authorizationTransfer.authorizationTransfer();
        if (authorization) {

            return   saveTransfer(pagador, recebedor, transferDto);
        }

        return new TransferDto(TRANSFERENCIA_NEGADA);
    }

    public TransferDto saveTransfer (User pagador, User recebedor, TransferDto transferDto){
        userRepository.save(pagador);
        userRepository.save(recebedor);
        transferRepository.save(modelMapper.map(transferDto, Transfer.class));
        
        return updateTransfer(transferDto, TRANSFERENCIA_REALIZADA, pagador, recebedor);
        
    }

    public TransferDto updateTransfer (TransferDto transferDto, String text, User pagador, User recebedor){
        transferDto.setText(text);
        transferDto.setSaldoPagador(pagador.getSaldo().doubleValue());
        transferDto.setSaldoRecebedor(recebedor.getSaldo().doubleValue());
        return transferDto;
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
