package com.gimnasio.gms.clases.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public record AsistenciaResponse(
        Long id,
        Long claseId,
        String nombreClase,
        Long miembroId,
        String nombreMiembro,
        LocalDate fecha,
        boolean asistio,
        LocalTime horaLlegada,
        LocalDateTime creadoEn
) {
}
