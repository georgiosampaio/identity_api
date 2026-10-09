package com.gens.identity_api.controller;

import com.gens.identity_api.dto.request.LoginRequestDTO;
import com.gens.identity_api.dto.response.LoginResponseDTO;
import com.gens.identity_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO requestDTO){
        return authService.authenticate(requestDTO);
    }




}
