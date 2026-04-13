package com.gimnasio.gms.clases.dto;

import com.gimnasio.gms.clases.entidad.DiaSemana;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalTime;

public record CrearClaseRequest(
        @NotNull(message = "El ID del instructor es obligatorio")
        Long instructorId,

        @NotBlank(message = "El nombre de la clase es obligatorio")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        String nombre,

        @NotNull(message = "El día de semana es obligatorio")
        DiaSemana diaSemana,

        @NotNull(message = "La hora de inicio es obligatoria")
        LocalTime horaInicio,

        @Min(value = 15, message = "La duración mínima es 15 minutos")
        Integer duracionMinutos,

        @Min(value = 1, message = "La capacidad mínima es 1")
        Integer capacidad
) {
}
