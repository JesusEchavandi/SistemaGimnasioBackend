package com.gimnasio.gms.clases.repositorio;

import com.gimnasio.gms.clases.entidad.Clase;
import com.gimnasio.gms.clases.entidad.DiaSemana;
import com.gimnasio.gms.clases.entidad.EstadoClase;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaseRepositorio extends JpaRepository<Clase, Long> {
    List<Clase> findByInstructorId(Long instructorId);

    List<Clase> findByDiaSemana(DiaSemana diaSemana);

    List<Clase> findByInstructorIdAndDiaSemana(Long instructorId, DiaSemana diaSemana);

    List<Clase> findByEstado(EstadoClase estado);

    Optional<Clase> findByNombreAndEstado(String nombre, EstadoClase estado);
}
