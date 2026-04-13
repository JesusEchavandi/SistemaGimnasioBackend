package com.gimnasio.gms.dashboard.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO de respuesta para reportes de membresías.
 * Contiene estadísticas agregadas de membresías.
 */
public record ReporteMembresiasResponse(
    Long totalMembresias,
    Long membresiasActivas,
    Long membresiasVencidas,
    Long membresiasAVencer,
    BigDecimal ingresoTotal,
    BigDecimal ingresoPromedio,
    List<MembresiasAVencerDetalleResponse> membresiasProximas
) {}
