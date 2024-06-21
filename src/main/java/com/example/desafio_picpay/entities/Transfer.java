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

    @Column(name = "email_pagador", nullable = false)
    private String  emailPagador;

    @Column(name = "email_recebedor", nullable = false)
    private String emailRecebedor;

    @Column(name = "valor", nullable = false)
    private Double valor;

}
