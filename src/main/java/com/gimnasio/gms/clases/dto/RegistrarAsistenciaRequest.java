package com.gimnasio.gms.clases.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record RegistrarAsistenciaRequest(
        @NotNull(message = "El ID de la clase es obligatorio")
        Long claseId,

        @NotNull(message = "El ID del miembro es obligatorio")
        Long miembroId,

        @NotNull(message = "La fecha es obligatoria")
        LocalDate fecha,

        @NotNull(message = "El estado de asistencia es obligatorio")
        boolean asistio,

        LocalTime horaLlegada
) {
}
