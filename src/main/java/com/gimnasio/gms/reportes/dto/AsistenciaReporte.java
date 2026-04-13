package com.gimnasio.gms.reportes.dto;

import java.time.LocalDate;

public record AsistenciaReporte(
        String nombreClase,
        String nombreInstructor,
        LocalDate fechaReporte,
        Integer cantidadAsistentes,
        Float tasaAsistenciaPorc,
        Float promedioPorSemana
) {
}
