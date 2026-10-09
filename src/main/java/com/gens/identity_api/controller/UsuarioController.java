package com.gens.identity_api.controller;

import com.gens.identity_api.dto.request.UsuarioRequestDTO;
import com.gens.identity_api.dto.response.UsuarioResponseDTO;
import com.gens.identity_api.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> createUser(@Valid @RequestBody UsuarioRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.saveUser(request));
    }

    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> findAllUsers() {
        return ResponseEntity.ok(usuarioService.findAllUsers());
    }

    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findUserById(id));
    }

    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> updateUser(@PathVariable Long id,
        @Valid @RequestBody UsuarioRequestDTO request) {
        return ResponseEntity.ok(usuarioService.updateUser(id, request));
    }

    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        usuarioService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
