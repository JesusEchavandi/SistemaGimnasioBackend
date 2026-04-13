package com.gimnasio.gms.seguridad.controlador;

import com.gimnasio.gms.comun.dto.RespuestaApi;
import com.gimnasio.gms.seguridad.dto.LoginRequest;
import com.gimnasio.gms.seguridad.dto.RefreshTokenRequest;
import com.gimnasio.gms.seguridad.dto.RegistroRequest;
import com.gimnasio.gms.seguridad.dto.TokenResponse;
import com.gimnasio.gms.seguridad.servicio.AutenticacionServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AutenticacionControlador {

    private final AutenticacionServicio autenticacionServicio;

    @PostMapping("/registro")
    public ResponseEntity<RespuestaApi<TokenResponse>> registro(@Valid @RequestBody RegistroRequest request) {
        return ResponseEntity.ok(RespuestaApi.ok("Registro exitoso", autenticacionServicio.registrar(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<RespuestaApi<TokenResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(RespuestaApi.ok("Autenticacion exitosa", autenticacionServicio.login(request)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RespuestaApi<TokenResponse>> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(RespuestaApi.ok("Token renovado", autenticacionServicio.refrescar(request)));
    }
}
