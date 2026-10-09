package com.gens.identity_api.service;

import com.gens.identity_api.dto.request.UsuarioRequestDTO;
import com.gens.identity_api.dto.response.UsuarioResponseDTO;
import com.gens.identity_api.infrastructure.entity.Usuario;
import com.gens.identity_api.infrastructure.exceptions.ConflictException;
import com.gens.identity_api.infrastructure.exceptions.NotFoundException;
import com.gens.identity_api.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public UsuarioResponseDTO saveUser(UsuarioRequestDTO request) {
        boolean emailExists = usuarioRepository.existsByEmail(request.getEmail());

        if (emailExists) {
            throw new ConflictException("Email já cadastrado");
        }
        Usuario user = new Usuario();

        user.setNome(request.getNome());
        user.setEmail(request.getEmail());
        user.setSenha(passwordEncoder.encode(request.getSenha()));
        user.setTelefone(request.getTelefone());

        Usuario savedUser = usuarioRepository.save(user);

        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        responseDTO.setId(savedUser.getId());
        responseDTO.setNome(savedUser.getNome());
        responseDTO.setEmail(savedUser.getEmail());
        responseDTO.setTelefone(savedUser.getTelefone());

        return responseDTO;
    }

    public List<UsuarioResponseDTO> findAllUsers() {
        List<Usuario> users = usuarioRepository.findAll();
        List<UsuarioResponseDTO> response = new ArrayList<>();
        for (Usuario user : users) {
            UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
            responseDTO.setId(user.getId());
            responseDTO.setNome(user.getNome());
            responseDTO.setEmail(user.getEmail());
            responseDTO.setTelefone(user.getTelefone());
            response.add(responseDTO);
        }
        return response;
    }

    public UsuarioResponseDTO findUserById(Long id) {
        Usuario user = usuarioRepository.findById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException("Usuário não encontrado");
        }
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setNome(user.getNome());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setTelefone(user.getTelefone());
        return responseDTO;
    }

    public UsuarioResponseDTO updateUser(Long id, UsuarioRequestDTO request) {
        Usuario user = usuarioRepository.findById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException("Usuario não encontrado");
        }
        if (!user.getEmail().equals(request.getEmail())
                && usuarioRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Usuario já cadastrado");
        }
        user.setNome(request.getNome());
        user.setEmail(request.getEmail());
        user.setSenha(passwordEncoder.encode(request.getSenha()));
        user.setTelefone(request.getTelefone());

        Usuario updatedUser = usuarioRepository.save(user);

        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        responseDTO.setId(updatedUser.getId());
        responseDTO.setNome(updatedUser.getNome());
        responseDTO.setEmail(updatedUser.getEmail());
        responseDTO.setTelefone(updatedUser.getTelefone());

        return responseDTO;
    }

    public void deleteUser(Long id) {
        Usuario user = usuarioRepository.findById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException("Usuario não encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
