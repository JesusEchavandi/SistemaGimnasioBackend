package com.gimnasio.gms.dashboard.dto;

/**
 * DTO para estadísticas de asistencia por clase.
 */
public record AsistenciaPorClaseResponse(
    Long claseId,
    String nombreClase,
    Integer capacidad,
    Long totalAsistencias,
    Double tasaAsistencia
) {}
