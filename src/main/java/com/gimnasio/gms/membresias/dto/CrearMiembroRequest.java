package com.gimnasio.gms.membresias.dto;

import com.gimnasio.gms.membresias.entidad.Sexo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record CrearMiembroRequest(
        @NotNull(message = "El ID de usuario es obligatorio")
        Long usuarioId,

        @NotBlank(message = "El número de membresia es obligatorio")
        @Size(min = 3, max = 50, message = "El número de membresia debe tener entre 3 y 50 caracteres")
        String numeroMembresia,

        @NotBlank(message = "El teléfono de contacto es obligatorio")
        @Pattern(regexp = "^[0-9+\\-\\s()]+$", message = "El teléfono no es válido")
        String telefonoContacto,

        LocalDate fechaNacimiento,

        @NotNull(message = "El sexo es obligatorio")
        Sexo sexo,

        @Pattern(regexp = "^[0-9+\\-\\s()]*$", message = "El teléfono de emergencia no es válido")
        String telefonoEmergencia
) {
}
