package com.example.desafio_picpay.service.Impl;

import com.example.desafio_picpay.dto.UserDto;
import com.example.desafio_picpay.entities.User;
import com.example.desafio_picpay.repository.UserRepository;
import com.example.desafio_picpay.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public UserDto saveUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);

        userRepository.save(user);

        return userDto;
    }

    @Override
    public List<UserDto> findAll() {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(UUID idPagador) {
        User user = userRepository.findById(idPagador).stream().iterator().next();
        return UserDto.builder()
                .cpf(user.getCpf())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    public Object findByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return modelMapper.map(user, UserDto.class);
        }
        return new Exception("Usuário não encontrado");
    }
}
