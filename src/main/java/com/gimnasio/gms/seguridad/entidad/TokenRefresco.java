package com.gimnasio.gms.seguridad.entidad;

import com.gimnasio.gms.comun.entidad.EntidadAuditada;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tokens_refresco")
public class TokenRefresco extends EntidadAuditada {

    @Column(nullable = false, unique = true, length = 500)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioSistema usuario;

    @Column(nullable = false)
    private LocalDateTime expiraEn;

    @Column(nullable = false)
    private boolean revocado = false;
}
