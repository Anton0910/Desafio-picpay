package com.example.desafio_picpay.service;

import com.example.desafio_picpay.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

     UserDto saveUser(UserDto userDto);

     UserDto findAll();
}
