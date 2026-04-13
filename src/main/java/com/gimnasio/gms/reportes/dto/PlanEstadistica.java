package com.gimnasio.gms.reportes.dto;

import java.math.BigDecimal;

public record PlanEstadistica(
        String nombrePlan,
        Integer totalMiembros,
        BigDecimal ingresoMensual
) {
}
