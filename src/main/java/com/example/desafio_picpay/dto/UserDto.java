package com.example.desafio_picpay.dto;

import com.example.desafio_picpay.entities.UserTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserDto(
        @NotNull
        String name,
        @NotNull
        String cpf,
        @NotNull
        String email,
        @NotNull
        String senha,
        @NotNull
        UserTypeEnum tipo) {
}
