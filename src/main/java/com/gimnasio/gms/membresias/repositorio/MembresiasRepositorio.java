package com.gimnasio.gms.membresias.repositorio;

import com.gimnasio.gms.membresias.entidad.EstadoMembresia;
import com.gimnasio.gms.membresias.entidad.Membresia;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MembresiasRepositorio extends JpaRepository<Membresia, Long> {
    List<Membresia> findByMiembroId(Long miembroId);

    List<Membresia> findByMiembroIdAndEstado(Long miembroId, EstadoMembresia estado);

    List<Membresia> findByEstado(EstadoMembresia estado);

    @Query("SELECT m FROM Membresia m WHERE m.estado = 'ACTIVA' AND m.fechaVencimiento BETWEEN :hoy AND :proximosDias")
    List<Membresia> findMembresiasProximasAVencer(@Param("hoy") LocalDate hoy, @Param("proximosDias") LocalDate proximosDias);
}
