package com.example.desafio_picpay.service.Impl;

import com.example.desafio_picpay.dto.UserDto;
import com.example.desafio_picpay.entities.User;
import com.example.desafio_picpay.repository.UserRepository;
import com.example.desafio_picpay.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
                .cpf_cnpj(userDto.getCpf())
                .email(userDto.getEmail())
                .nomeCompleto(userDto.getName())
                .senha(userDto.getSenha())
                .tipoUsuario(userDto.getTipo())
                .build();

        userRepository.save(user);

        return userDto;
    }

    @Override
    public UserDto findAll() {
        User user = userRepository.findAll().iterator().next();
        return  UserDto.builder()
                .cpf(user.getCpf_cnpj())
                .name(user.getNomeCompleto())
                .email(user.getEmail())
                .build();
    }

    @Override
    public UserDto findById(UUID idPagador) {
        User user = userRepository.findById(idPagador).stream().iterator().next();
        return UserDto.builder()
                .cpf(user.getCpf_cnpj())
                .name(user.getNomeCompleto())
                .email(user.getEmail())
                .build();
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }


}
