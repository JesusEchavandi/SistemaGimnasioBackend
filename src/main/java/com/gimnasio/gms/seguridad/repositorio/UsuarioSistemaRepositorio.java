package com.gimnasio.gms.seguridad.repositorio;

import com.gimnasio.gms.seguridad.entidad.UsuarioSistema;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioSistemaRepositorio extends JpaRepository<UsuarioSistema, Long> {
    Optional<UsuarioSistema> findByNombreUsuario(String nombreUsuario);

    boolean existsByNombreUsuario(String nombreUsuario);

    boolean existsByCorreo(String correo);
}
