package com.gimnasio.gms.membresias.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ActualizarMiembroRequest(
        @Size(min = 3, max = 50, message = "El número de membresia debe tener entre 3 y 50 caracteres")
        String numeroMembresia,

        @Size(min = 1, max = 20, message = "El teléfono debe ser válido")
        String telefonoContacto,

        String telefonoEmergencia
) {
}
