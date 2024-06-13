package com.example.desafio_picpay.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.util.UUID;

@Data
@Builder
public class TransferDto {
    @ReadOnlyProperty
    private UUID idPagador;
    @ReadOnlyProperty
    private UUID idRecebedor;

    private Double valor;

    private Double saldoPagador;

    private Double saldoRecebedor;

    private String text;

}