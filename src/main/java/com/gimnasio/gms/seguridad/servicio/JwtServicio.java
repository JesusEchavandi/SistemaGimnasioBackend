package com.gimnasio.gms.seguridad.servicio;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtServicio {

    @Value("${jwt.secreto}")
    private String secreto;

    @Value("${jwt.expiracion-minutos}")
    private long expiracionMinutos;

    public String generarTokenAcceso(String nombreUsuario, String rol) {
        Instant ahora = Instant.now();
        Instant vencimiento = ahora.plus(expiracionMinutos, ChronoUnit.MINUTES);

        return Jwts.builder()
                .subject(nombreUsuario)
                .claim("rol", rol)
                .issuedAt(Date.from(ahora))
                .expiration(Date.from(vencimiento))
                .signWith(claveFirma())
                .compact();
    }

    public String extraerNombreUsuario(String token) {
        return extraerClaims(token).getSubject();
    }

    public boolean esValido(String token) {
        try {
            Claims claims = extraerClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception ex) {
            return false;
        }
    }

    public long expiracionSegundos() {
        return expiracionMinutos * 60;
    }

    private Claims extraerClaims(String token) {
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) claveFirma())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Key claveFirma() {
        byte[] keyBytes = Decoders.BASE64.decode(java.util.Base64.getEncoder().encodeToString(secreto.getBytes()));
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
