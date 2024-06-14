package com.example.desafio_picpay.dto;

import lombok.Data;

@Data
public class ResponseAuthorization {
    private Data data;
    private String status;

    @lombok.Data
    public static class Data {
        private boolean authorization;
    }
}
