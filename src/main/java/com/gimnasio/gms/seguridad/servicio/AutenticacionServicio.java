package com.gimnasio.gms.seguridad.servicio;

import com.gimnasio.gms.comun.excepcion.ExcepcionNegocio;
import com.gimnasio.gms.seguridad.dto.LoginRequest;
import com.gimnasio.gms.seguridad.dto.RefreshTokenRequest;
import com.gimnasio.gms.seguridad.dto.RegistroRequest;
import com.gimnasio.gms.seguridad.dto.TokenResponse;
import com.gimnasio.gms.seguridad.entidad.TokenRefresco;
import com.gimnasio.gms.seguridad.entidad.UsuarioSistema;
import com.gimnasio.gms.seguridad.repositorio.TokenRefrescoRepositorio;
import com.gimnasio.gms.seguridad.repositorio.UsuarioSistemaRepositorio;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AutenticacionServicio {

    private final UsuarioSistemaRepositorio usuarioSistemaRepositorio;
    private final TokenRefrescoRepositorio tokenRefrescoRepositorio;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtServicio jwtServicio;

    @Value("${jwt.refresh-expiracion-dias}")
    private long refreshExpiracionDias;

    @Transactional
    public TokenResponse registrar(RegistroRequest request) {
        if (usuarioSistemaRepositorio.existsByNombreUsuario(request.nombreUsuario())) {
            throw new ExcepcionNegocio("El nombre de usuario ya existe");
        }
        if (usuarioSistemaRepositorio.existsByCorreo(request.correo())) {
            throw new ExcepcionNegocio("El correo ya existe");
        }

        UsuarioSistema usuario = new UsuarioSistema();
        usuario.setNombreUsuario(request.nombreUsuario());
        usuario.setCorreo(request.correo());
        usuario.setClave(passwordEncoder.encode(request.clave()));
        usuario.setRol(request.rol());
        usuarioSistemaRepositorio.save(usuario);

        return generarTokens(usuario);
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.nombreUsuario(), request.clave()));

        UsuarioSistema usuario = usuarioSistemaRepositorio.findByNombreUsuario(request.nombreUsuario())
                .orElseThrow(() -> new ExcepcionNegocio("Credenciales invalidas"));

        return generarTokens(usuario);
    }

    @Transactional
    public TokenResponse refrescar(RefreshTokenRequest request) {
        TokenRefresco tokenRefresco = tokenRefrescoRepositorio.findByTokenAndRevocadoFalse(request.refreshToken())
                .orElseThrow(() -> new ExcepcionNegocio("Refresh token invalido"));

        if (tokenRefresco.getExpiraEn().isBefore(LocalDateTime.now())) {
            throw new ExcepcionNegocio("Refresh token expirado");
        }

        UsuarioSistema usuario = tokenRefresco.getUsuario();
        tokenRefresco.setRevocado(true);
        tokenRefrescoRepositorio.save(tokenRefresco);

        return generarTokens(usuario);
    }

    private TokenResponse generarTokens(UsuarioSistema usuario) {
        tokenRefrescoRepositorio.revocarPorUsuario(usuario.getId());
        tokenRefrescoRepositorio.eliminarExpirados(LocalDateTime.now());

        String accessToken = jwtServicio.generarTokenAcceso(usuario.getNombreUsuario(), usuario.getRol().name());
        String refreshToken = UUID.randomUUID().toString() + UUID.randomUUID();

        TokenRefresco tr = new TokenRefresco();
        tr.setToken(refreshToken);
        tr.setUsuario(usuario);
        tr.setExpiraEn(LocalDateTime.now().plusDays(refreshExpiracionDias));
        tokenRefrescoRepositorio.save(tr);

        return new TokenResponse(accessToken, refreshToken, "Bearer", jwtServicio.expiracionSegundos());
    }
}
