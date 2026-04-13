package com.gimnasio.gms.membresias.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record CrearPlanRequest(
        @NotBlank(message = "El nombre del plan es obligatorio")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        String nombre,

        @NotBlank(message = "La descripción es obligatoria")
        @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 y 500 caracteres")
        String descripcion,

        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        BigDecimal precioMensual,

        @Min(value = 1, message = "La duración debe ser al menos 1 día")
        Integer duracionDias
) {
}
