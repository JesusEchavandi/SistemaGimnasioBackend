package com.gimnasio.gms.membresias.dto;

import com.gimnasio.gms.membresias.entidad.EstadoMembresia;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record MembresiasResponse(
        Long id,
        Long miembroId,
        String nombreMiembro,
        Long planId,
        String nombrePlan,
        LocalDate fechaInicio,
        LocalDate fechaVencimiento,
        EstadoMembresia estado,
        boolean conAlerta,
        Integer diasRestantes,
        LocalDateTime creadoEn
) {
}
