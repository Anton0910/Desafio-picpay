package com.example.desafio_picpay.service.serviceImpl;

import com.example.desafio_picpay.dto.UserDto;
import com.example.desafio_picpay.entities.User;
import com.example.desafio_picpay.repository.UserRepository;
import com.example.desafio_picpay.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDto saveUser(UserDto userDto){
        User user = User.builder()
                .cpf_cnpj(userDto.cpf())
                .email(userDto.email())
                .nomeCompleto(userDto.name())
                .senha(userDto.senha())
                .tipo(userDto.tipo())
                .build();

        userRepository.save(user);

        return userDto;
    }

    @Override
    public UserDto findAll() {
        User user = userRepository.findAll().iterator().next();
        return  UserDto.builder()
                .cpf(user.getCpf_cnpj()).build();
    }
}
