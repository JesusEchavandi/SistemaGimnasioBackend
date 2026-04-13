package com.gimnasio.gms.seguridad.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        String tipo,
        long expiracionSegundos
) {
}
