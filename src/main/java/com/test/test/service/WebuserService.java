package com.test.test.service;

import com.test.test.controller.auth.AuthenticationRequest;
import com.test.test.controller.auth.AuthenticationResponse;
import com.test.test.controller.auth.RegisterRequest;
import com.test.test.model.UserRole;
import com.test.test.model.Webuser;
import com.test.test.repository.WebuserRepository;
import com.test.test.util.EncodingSHA256;
import com.test.test.util.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class WebuserService {

    @Autowired
    WebuserRepository webuserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    public AuthenticationResponse addUser(RegisterRequest request) throws NoSuchAlgorithmException {
        Webuser webuserToAdd = Webuser.builder()
                .id(UUID.randomUUID())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .role(UserRole.USER)
                .build();
        webuserRepository.save(webuserToAdd);
        var jwtToken = jwtService.generateToken(webuserToAdd);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse addAdmin(RegisterRequest request) throws NoSuchAlgorithmException {
        Webuser webuserToAdd = Webuser.builder()
                .id(UUID.randomUUID())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .role(UserRole.ADMIN)
                .build();
        webuserRepository.save(webuserToAdd);
        var jwtToken = jwtService.generateToken(webuserToAdd);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            )
        );

        var user = webuserRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public Optional<Webuser> getUser(UUID id) {
        return webuserRepository.findById(id);
    }

}
