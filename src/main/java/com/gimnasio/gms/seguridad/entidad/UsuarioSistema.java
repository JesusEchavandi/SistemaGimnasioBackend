package com.gimnasio.gms.seguridad.entidad;

import com.gimnasio.gms.comun.entidad.EntidadAuditada;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuarios_sistema")
public class UsuarioSistema extends EntidadAuditada {

    @Column(nullable = false, unique = true, length = 120)
    private String nombreUsuario;

    @Column(nullable = false, unique = true, length = 180)
    private String correo;

    @Column(nullable = false)
    private String clave;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RolUsuario rol;

    @Column(nullable = false)
    private boolean activo = true;
}
