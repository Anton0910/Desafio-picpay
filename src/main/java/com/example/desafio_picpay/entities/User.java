package com.example.desafio_picpay.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cpf"),
        @UniqueConstraint(columnNames = "email")
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_usuario", updatable = false, unique = true, nullable = false)
    private UUID id_usuario;

    @Column(name = "nomecompleto", updatable = false, unique = true, nullable = false)
    private String name;

    @Column(unique = true, name = "cpf_cnpj", nullable = false)
    private String cpf;

    @Column(unique = true, name = "email", nullable = false)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "tipo", nullable = false)
    private String tipoUsuario;

    @Column(name = "saldo", nullable = false)
    private BigDecimal saldo;

}
