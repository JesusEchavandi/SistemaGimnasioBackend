package com.gimnasio.gms.membresias.dto;

import com.gimnasio.gms.membresias.entidad.Sexo;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record MiembroResponse(
        Long id,
        Long usuarioId,
        String nombreUsuario,
        String numeroMembresia,
        String telefonoContacto,
        LocalDate fechaNacimiento,
        Sexo sexo,
        String telefonoEmergencia,
        LocalDateTime creadoEn,
        LocalDateTime actualizadoEn
) {
}
