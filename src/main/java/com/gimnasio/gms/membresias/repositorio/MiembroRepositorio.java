package com.gimnasio.gms.membresias.repositorio;

import com.gimnasio.gms.membresias.entidad.Miembro;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MiembroRepositorio extends JpaRepository<Miembro, Long> {
    Optional<Miembro> findByNumeroMembresia(String numeroMembresia);

    Optional<Miembro> findByUsuarioId(Long usuarioId);

    boolean existsByNumeroMembresia(String numeroMembresia);

    boolean existsByUsuarioId(Long usuarioId);
}
