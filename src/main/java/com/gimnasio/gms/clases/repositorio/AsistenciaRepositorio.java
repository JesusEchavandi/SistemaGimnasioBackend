package com.gimnasio.gms.clases.repositorio;

import com.gimnasio.gms.clases.entidad.Asistencia;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AsistenciaRepositorio extends JpaRepository<Asistencia, Long> {
    List<Asistencia> findByClaseId(Long claseId);

    List<Asistencia> findByClaseIdAndFecha(Long claseId, LocalDate fecha);

    List<Asistencia> findByMiembroId(Long miembroId);

    Optional<Asistencia> findByClaseIdAndMiembroIdAndFecha(Long claseId, Long miembroId, LocalDate fecha);

    @Query("SELECT a FROM Asistencia a WHERE a.clase.id = :claseId AND a.asistio = true AND a.fecha = :fecha")
    List<Asistencia> findAsistenciasEnClase(@Param("claseId") Long claseId, @Param("fecha") LocalDate fecha);

    @Query("SELECT COUNT(a) FROM Asistencia a WHERE a.clase.id = :claseId AND a.asistio = true")
    Integer countAsistenciasEnClase(@Param("claseId") Long claseId);
}
