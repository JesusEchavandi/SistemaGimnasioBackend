package com.gimnasio.gms.reportes.dto;

public record ClaseAsistenciaInfo(
        String nombreClase,
        String nombreInstructor,
        Integer totalAsistencias,
        Float tasaPromedio
) {
}
