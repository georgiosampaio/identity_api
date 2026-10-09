package com.gens.identity_api.infrastructure.exceptions;

import com.gens.identity_api.dto.response.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.authentication.BadCredentialsException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflict(ConflictException exception) {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO();
        responseDTO.setStatus(HttpStatus.CONFLICT.value());
        responseDTO.setMensagem(exception.getMessage());
        responseDTO.setErros(Collections.emptyMap());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDTO);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnauthorized(UnauthorizedException exception) {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO();
        responseDTO.setStatus(HttpStatus.UNAUTHORIZED.value());
        responseDTO.setMensagem(exception.getMessage());
        responseDTO.setErros(Collections.emptyMap());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(NotFoundException exception) {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO();
        responseDTO.setStatus(HttpStatus.NOT_FOUND.value());
        responseDTO.setMensagem(exception.getMessage());
        responseDTO.setErros(Collections.emptyMap());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(MethodArgumentNotValidException exception) {

        Map<String, String> message = new HashMap<>();

        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            message.put(error.getField(), error.getDefaultMessage());
        }

        ErrorResponseDTO responseDTO = new ErrorResponseDTO();
        responseDTO.setStatus(HttpStatus.BAD_REQUEST.value());
        responseDTO.setMensagem("Erro de validação");
        responseDTO.setErros(message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentials() {

        ErrorResponseDTO responseDTO = new ErrorResponseDTO();
        responseDTO.setStatus(HttpStatus.UNAUTHORIZED.value());
        responseDTO.setMensagem("Credenciais inválidas");
        responseDTO.setErros(Collections.emptyMap());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleInternalServerError(Exception exception){
        log.error("Erro interno do servidor", exception);
        ErrorResponseDTO responseDTO = new ErrorResponseDTO();
        responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseDTO.setMensagem("Erro interno do servidor");
        responseDTO.setErros(Collections.emptyMap());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
    }
}
