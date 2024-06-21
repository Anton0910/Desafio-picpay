package com.example.desafio_picpay.service;

import com.example.desafio_picpay.dto.UserDto;
import com.example.desafio_picpay.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {

     UserDto saveUser(UserDto userDto);

     List<UserDto> findAll();

     UserDto findById(UUID idPagador);

     Object findByEmail(String email) throws Exception ;
}
