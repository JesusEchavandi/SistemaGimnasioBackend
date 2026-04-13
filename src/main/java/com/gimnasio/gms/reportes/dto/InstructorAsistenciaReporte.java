package com.gimnasio.gms.reportes.dto;

public record InstructorAsistenciaReporte(
        String nombreInstructor,
        Integer totalClases,
        Integer totalAsistenciasRegistradas,
        Float tasaPromedioPorClase
) {
}
