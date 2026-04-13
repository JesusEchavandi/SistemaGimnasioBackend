package com.gimnasio.gms.reportes.dto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardResumen(
        Integer totalMiembros,
        Integer membresiaActivas,
        Integer membresiaVencidas,
        BigDecimal ingresoMesActual,
        List<ClaseAsistenciaInfo> clasesMasAsistidas,
        PlanEstadistica planMasPopular
) {
}
