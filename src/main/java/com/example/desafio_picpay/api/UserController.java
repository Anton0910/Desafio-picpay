package com.example.desafio_picpay.api;

import com.example.desafio_picpay.dto.UserDto;
import com.example.desafio_picpay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequestMapping(value = "api/user")
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/cadastro")
    public ResponseEntity<UserDto> cadastro(@RequestBody UserDto userDto) {

        return ResponseEntity.ok().body(userService.saveUser(userDto));
    }

    @GetMapping(value = "cadastro")
    public ResponseEntity<UserDto> findAll() {

        return ResponseEntity.ok().body(userService.findAll());
    }
}
