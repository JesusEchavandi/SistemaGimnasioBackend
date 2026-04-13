package com.gimnasio.gms.membresias.entidad;

import com.gimnasio.gms.comun.entidad.EntidadAuditada;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "planes")
public class Plan extends EntidadAuditada {

    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioMensual;

    @Column(nullable = false)
    private Integer duracionDias;

    @Column(nullable = false)
    private boolean activo = true;
}
