package com.gimnasio.gms.clases.entidad;

import com.gimnasio.gms.comun.entidad.EntidadAuditada;
import com.gimnasio.gms.membresias.entidad.Miembro;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "asistencias", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"clase_id", "miembro_id", "fecha"})
})
public class Asistencia extends EntidadAuditada {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "clase_id", nullable = false)
    private Clase clase;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "miembro_id", nullable = false)
    private Miembro miembro;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private boolean asistio;

    @Column(nullable = true)
    private LocalTime horaLlegada;
}
