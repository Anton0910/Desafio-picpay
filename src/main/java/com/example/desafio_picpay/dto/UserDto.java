package com.example.desafio_picpay.dto;

import com.example.desafio_picpay.entities.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
        @NotNull
        private String name;
        @NotNull
        private String cpf;
        @NotNull
        private String email;
        @NotNull
        @ReadOnlyProperty
        private String senha;
        @NotNull
        private BigDecimal saldo;
        @NotNull
        private String tipoUsuario;


}