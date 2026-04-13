package com.gimnasio.gms.membresias.dto;

import java.time.LocalDate;

public record MembresiasAVencerResponse(
        Long id,
        Long miembroId,
        String nombreMiembro,
        String numeroMembresia,
        String planNombre,
        LocalDate fechaVencimiento,
        Integer diasRestantes
) {
}
