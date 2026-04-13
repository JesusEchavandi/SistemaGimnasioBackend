package com.gimnasio.gms.clases.dto;

import com.gimnasio.gms.clases.entidad.DiaSemana;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.time.LocalTime;

public record ActualizarClaseRequest(
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        String nombre,

        DiaSemana diaSemana,

        LocalTime horaInicio,

        @Min(value = 15, message = "La duración mínima es 15 minutos")
        Integer duracionMinutos,

        @Min(value = 1, message = "La capacidad mínima es 1")
        Integer capacidad
) {
}
