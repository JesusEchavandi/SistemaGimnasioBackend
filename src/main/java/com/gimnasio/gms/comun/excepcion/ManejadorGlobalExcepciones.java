package com.gimnasio.gms.comun.excepcion;

import com.gimnasio.gms.comun.dto.RespuestaApi;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ManejadorGlobalExcepciones {

    @ExceptionHandler(ExcepcionNoEncontrado.class)
    public ResponseEntity<RespuestaApi<Void>> manejarNoEncontrado(ExcepcionNoEncontrado ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(RespuestaApi.error(ex.getMessage()));
    }

    @ExceptionHandler(ExcepcionNegocio.class)
    public ResponseEntity<RespuestaApi<Void>> manejarNegocio(ExcepcionNegocio ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(RespuestaApi.error(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespuestaApi<Void>> manejarValidaciones(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(" | "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(RespuestaApi.error(mensaje));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RespuestaApi<Void>> manejarConstraint(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(RespuestaApi.error(ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<RespuestaApi<Void>> manejarBadCredentials(BadCredentialsException ex) {
        log.warn("Credenciales inválidas: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(RespuestaApi.error("Credenciales inválidas"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespuestaApi<Void>> manejarGeneral(Exception ex) {
        log.error("Error no manejado: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(RespuestaApi.error("Error interno del servidor: " + ex.getMessage()));
    }
}
