package com.gimnasio.gms.clases.entidad;

import com.gimnasio.gms.comun.entidad.EntidadAuditada;
import com.gimnasio.gms.seguridad.entidad.UsuarioSistema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "instructores")
public class Instructor extends EntidadAuditada {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private UsuarioSistema usuario;

    @Column(nullable = false, length = 100)
    private String especialidad;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String certificaciones;

    @Column(nullable = false)
    private boolean activo = true;
}
