package com.gimnasio.gms.clases.dto;

import jakarta.validation.constraints.Size;

public record ActualizarInstructorRequest(
        @Size(min = 3, max = 100, message = "La especialidad debe tener entre 3 y 100 caracteres")
        String especialidad,

        @Size(max = 500, message = "Las certificaciones no pueden exceder 500 caracteres")
        String certificaciones
) {
}
