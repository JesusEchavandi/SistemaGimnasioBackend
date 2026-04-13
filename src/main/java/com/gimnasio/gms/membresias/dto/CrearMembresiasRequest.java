package com.gimnasio.gms.membresias.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CrearMembresiasRequest(
        @NotNull(message = "El ID del miembro es obligatorio")
        Long miembroId,

        @NotNull(message = "El ID del plan es obligatorio")
        Long planId,

        @NotNull(message = "La fecha de inicio es obligatoria")
        LocalDate fechaInicio
) {
}
