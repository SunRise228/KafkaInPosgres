package com.test.test.controller.auth;

import com.test.test.listener.websocketListener.WebSocketListener;
import com.test.test.model.Webuser;
import com.test.test.service.WebuserService;
import com.test.test.util.EncodingSHA256;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
@RequestMapping("/auth")
@Log4j2
@Tag(name = "OAuth2")
public class WebuserRestController {

    @Autowired
    WebuserService webuserService;

    @Operation(summary = "Создать нового пользователя системы",
            description = "Добавление пользователя в базу данных.")
    @PostMapping("/register/user")
    public ResponseEntity<AuthenticationResponse> addUser(@RequestBody RegisterRequest request) throws NoSuchAlgorithmException {
        return ResponseEntity.ok(webuserService.addUser(request));
    }

    @Operation(summary = "Создать нового админа системы",
            description = "Добавление админа в базу данных.")
    @PostMapping("/register/admin")
    public ResponseEntity<AuthenticationResponse> addAdmin(@RequestBody RegisterRequest request) throws NoSuchAlgorithmException {
        return ResponseEntity.ok(webuserService.addAdmin(request));
    }

    @Operation(summary = "Ауентификация на сервере",
            description = "Ауентификация пользователя на сервере")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(webuserService.authenticate(request));
    }

}
