package com.example.desafio_picpay.itg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public interface AuthorizationTransfer {
    public Boolean authorizationTransfer();

}
