package com.gimnasio.gms.membresias.entidad;

import com.gimnasio.gms.comun.entidad.EntidadAuditada;
import com.gimnasio.gms.seguridad.entidad.UsuarioSistema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "miembros")
public class Miembro extends EntidadAuditada {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private UsuarioSistema usuario;

    @Column(nullable = false, unique = true, length = 50)
    private String numeroMembresia;

    @Column(nullable = false, length = 20)
    private String telefonoContacto;

    @Column(nullable = true)
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Sexo sexo;

    @Column(nullable = true, length = 20)
    private String telefonoEmergencia;
}
