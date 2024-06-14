package com.example.desafio_picpay.dto;

import com.example.desafio_picpay.entities.Transfer;
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

  public TransferDto (String text){
      this.text = text;
  }

}