package com.example.desafio_picpay.itg.impl;

import com.example.desafio_picpay.dto.ResponseAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthorizationTransferItgImpl {


    private final RestTemplate restTemplate;

    @Value("${url.authorization}")
    private String url;

    @Autowired
    public AuthorizationTransferItgImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Boolean authorizationTransfer(){
        ResponseAuthorization response = restTemplate.exchange(url, HttpMethod.GET, null, ResponseAuthorization.class).getBody();
        assert response != null;
        return response.getStatus().equals("success") && response.getData().isAuthorization();
    }
}
