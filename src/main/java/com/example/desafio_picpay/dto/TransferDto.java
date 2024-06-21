package com.example.desafio_picpay.dto;

import com.example.desafio_picpay.entities.Transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {

    private String emailPagador;

    private String emailRecebedor;

    private Double valor;

    private Double saldoPagador;

    private Double saldoRecebedor;

    private String text;

  public TransferDto (String text){
      this.text = text;
  }

}