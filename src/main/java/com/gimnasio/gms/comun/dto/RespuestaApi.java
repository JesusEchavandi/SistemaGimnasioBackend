package com.gimnasio.gms.comun.dto;

import java.time.LocalDateTime;

public record RespuestaApi<T>(
        boolean exito,
        String mensaje,
        T datos,
        LocalDateTime marcaTiempo
) {
    public static <T> RespuestaApi<T> ok(String mensaje, T datos) {
        return new RespuestaApi<>(true, mensaje, datos, LocalDateTime.now());
    }

    public static <T> RespuestaApi<T> error(String mensaje) {
        return new RespuestaApi<>(false, mensaje, null, LocalDateTime.now());
    }
}
