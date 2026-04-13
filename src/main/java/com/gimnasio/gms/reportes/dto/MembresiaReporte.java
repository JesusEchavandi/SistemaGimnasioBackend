package com.gimnasio.gms.reportes.dto;

import java.math.BigDecimal;

public record MembresiaReporte(
        String planNombre,
        Integer cantidadActivas,
        Integer cantidadVencidas,
        BigDecimal ingresoTotalMes
) {
}
