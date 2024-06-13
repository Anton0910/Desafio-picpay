package com.example.desafio_picpay.dto;

import com.example.desafio_picpay.entities.User;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UserDto {
        @NotNull
        private String name;
        @NotNull
        private String cpf;
        @NotNull
        private String email;
        @NotNull
        private String senha;
        @NotNull
        private BigDecimal saldo;
        @NotNull
        private String tipo;

        public static User userDtoToUser(UserDto user){
                return User.builder()
                        .cpf_cnpj(user.getCpf())
                        .email(user.getEmail())
                        .nomeCompleto(user.getName())
                        .senha(user.getSenha())
                        .tipoUsuario(user.getTipo())
                        .saldo(user.getSaldo())
                        .build();
        }
}