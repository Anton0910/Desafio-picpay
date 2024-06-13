package com.example.desafio_picpay.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_transferencia", updatable = false, unique = true, nullable = false)
    private UUID idTransferencia;

    @Column(name = "id_pagador", nullable = false)
    private UUID idPagador;

    @Column(name = "id_recebedor", nullable = false)
    private UUID idRecebedor;

    @Column(name = "valor", nullable = false)
    private Double valor;

}
