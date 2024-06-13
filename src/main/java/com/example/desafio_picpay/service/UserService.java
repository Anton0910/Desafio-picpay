package com.example.desafio_picpay.service;

import com.example.desafio_picpay.dto.UserDto;
import com.example.desafio_picpay.entities.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService {

     UserDto saveUser(UserDto userDto);

     UserDto findAll();

     UserDto findById(UUID idPagador);
}
