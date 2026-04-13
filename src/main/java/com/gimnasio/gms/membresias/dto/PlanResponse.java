package com.gimnasio.gms.membresias.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PlanResponse(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal precioMensual,
        Integer duracionDias,
        boolean activo,
        LocalDateTime creadoEn
) {
}
