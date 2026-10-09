package com.gens.identity_api.service;

import com.gens.identity_api.infrastructure.entity.Usuario;
import com.gens.identity_api.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {
    private final UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Usuario não encontrado")
        );

        return User.withUsername(user.getEmail())
                .password(user.getSenha())
                .build();
    }
}
