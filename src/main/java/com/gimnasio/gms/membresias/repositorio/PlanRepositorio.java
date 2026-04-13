package com.gimnasio.gms.membresias.repositorio;

import com.gimnasio.gms.membresias.entidad.Plan;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepositorio extends JpaRepository<Plan, Long> {
    Optional<Plan> findByNombreAndActivo(String nombre, boolean activo);

    List<Plan> findByActivoTrue();

    boolean existsByNombreAndActivo(String nombre, boolean activo);
}
