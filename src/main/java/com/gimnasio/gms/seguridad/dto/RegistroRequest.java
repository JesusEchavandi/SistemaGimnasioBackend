package com.gimnasio.gms.seguridad.dto;

import com.gimnasio.gms.seguridad.entidad.RolUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistroRequest(
        @NotBlank(message = "El nombre de usuario es obligatorio")
        @Size(min = 4, max = 120, message = "El nombre de usuario debe tener entre 4 y 120 caracteres")
        String nombreUsuario,

        @Email(message = "El correo no es valido")
        @NotBlank(message = "El correo es obligatorio")
        String correo,

        @NotBlank(message = "La clave es obligatoria")
        @Size(min = 8, max = 100, message = "La clave debe tener entre 8 y 100 caracteres")
        String clave,

        @NotNull(message = "El rol es obligatorio")
        RolUsuario rol
) {
}
