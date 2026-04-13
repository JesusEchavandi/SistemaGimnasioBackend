package com.gimnasio.gms.dashboard.dto;

import java.util.List;

/**
 * DTO de respuesta para reportes de asistencia.
 * Contiene estadísticas agregadas de asistencia.
 */
public record ReporteAsistenciaResponse(
    Long totalRegistros,
    Long totalClases,
    Long totalMiembros,
    Double tasaAsistenciaPromedio,
    Double tasaInasistenciaPromedio,
    List<AsistenciaPorClaseResponse> asistenciaPorClase
) {}
