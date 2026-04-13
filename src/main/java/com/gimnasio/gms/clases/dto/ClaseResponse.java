package com.gimnasio.gms.clases.dto;

import com.gimnasio.gms.clases.entidad.DiaSemana;
import com.gimnasio.gms.clases.entidad.EstadoClase;
import java.time.LocalTime;
import java.time.LocalDateTime;

public record ClaseResponse(
        Long id,
        String nombre,
        Long instructorId,
        String nombreInstructor,
        DiaSemana diaSemana,
        LocalTime horaInicio,
        Integer duracionMinutos,
        Integer capacidad,
        EstadoClase estado,
        LocalDateTime creadoEn
) {
}
