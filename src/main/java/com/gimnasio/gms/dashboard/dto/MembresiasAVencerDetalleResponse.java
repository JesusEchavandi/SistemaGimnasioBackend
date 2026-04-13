package com.gimnasio.gms.dashboard.dto;

import java.time.LocalDate;

/**
 * DTO para detalles de membresías próximas a vencer.
 */
public record MembresiasAVencerDetalleResponse(
    Long membresiaId,
    Long miembroId,
    String numeroMembresia,
    LocalDate fechaVencimiento,
    Integer diasRestantes
) {}
