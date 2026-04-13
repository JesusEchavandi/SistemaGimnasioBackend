package com.gimnasio.gms.clases.dto;

import java.time.LocalDateTime;

public record InstructorResponse(
        Long id,
        Long usuarioId,
        String nombreUsuario,
        String especialidad,
        String certificaciones,
        boolean activo,
        LocalDateTime creadoEn
) {
}
