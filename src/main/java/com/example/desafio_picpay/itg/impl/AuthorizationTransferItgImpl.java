package com.example.desafio_picpay.itg.impl;

import com.example.desafio_picpay.dto.ResponseAuthorization;
import com.example.desafio_picpay.itg.AuthorizationTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthorizationTransferItgImpl implements AuthorizationTransfer {


    private final RestTemplate restTemplate;

    @Value("${url.authorization}")
    private String url;

    @Autowired
    public AuthorizationTransferItgImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Boolean authorizationTransfer(){
        try {
            ResponseAuthorization response = restTemplate.exchange(url, HttpMethod.GET, null, ResponseAuthorization.class).getBody();
            return response.getStatus().equals("success") && response.getData().isAuthorization();
        } catch (HttpClientErrorException.Forbidden e) {
            return false;
        }
    }
}
