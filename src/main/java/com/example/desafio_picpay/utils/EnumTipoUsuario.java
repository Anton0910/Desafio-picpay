package com.example.desafio_picpay.utils;

import jakarta.persistence.Enumerated;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
public enum EnumTipoUsuario {
    COMUM,
    LOJISTA
}
