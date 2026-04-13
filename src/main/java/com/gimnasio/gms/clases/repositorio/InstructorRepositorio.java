package com.gimnasio.gms.clases.repositorio;

import com.gimnasio.gms.clases.entidad.Instructor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepositorio extends JpaRepository<Instructor, Long> {
    Optional<Instructor> findByUsuarioId(Long usuarioId);

    boolean existsByUsuarioId(Long usuarioId);

    java.util.List<Instructor> findByActivoTrue();
}
