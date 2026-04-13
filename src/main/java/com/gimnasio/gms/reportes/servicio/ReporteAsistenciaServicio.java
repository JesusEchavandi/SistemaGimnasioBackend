package com.gimnasio.gms.reportes.servicio;

import com.gimnasio.gms.clases.repositorio.AsistenciaRepositorio;
import com.gimnasio.gms.clases.repositorio.ClaseRepositorio;
import com.gimnasio.gms.reportes.dto.AsistenciaReporte;
import com.gimnasio.gms.reportes.dto.ClaseAsistenciaInfo;
import com.gimnasio.gms.reportes.dto.InstructorAsistenciaReporte;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReporteAsistenciaServicio {

    private final AsistenciaRepositorio asistenciaRepositorio;
    private final ClaseRepositorio claseRepositorio;

    @Transactional(readOnly = true)
    public List<ClaseAsistenciaInfo> obtenerAsistenciasPorClase(Long claseId) {
        log.info("Generando reporte de asistencia para clase: {}", claseId);

        var asistencias = asistenciaRepositorio.findByClaseId(claseId);

        if (asistencias.isEmpty()) {
            return List.of();
        }

        var clase = asistencias.get(0).getClase();
        long totalAsistentes = asistencias.stream()
                .filter(a -> a.isAsistio())
                .count();

        float tasaPromedio = (float) totalAsistentes / asistencias.size() * 100;

        return List.of(new ClaseAsistenciaInfo(
                clase.getNombre(),
                clase.getInstructor().getUsuario().getNombreUsuario(),
                asistencias.size(),
                tasaPromedio
        ));
    }

    @Transactional(readOnly = true)
    public List<AsistenciaReporte> obtenerAsistenciaPorClaseYPeriodo(Long claseId, LocalDate fechaInicio, LocalDate fechaFin) {
        log.info("Generando reporte de asistencia para clase {} en período {} a {}", claseId, fechaInicio, fechaFin);

        var asistencias = asistenciaRepositorio.findByClaseId(claseId);

        var asistenciasDelPeriodo = asistencias.stream()
                .filter(a -> !a.getFecha().isBefore(fechaInicio) && !a.getFecha().isAfter(fechaFin))
                .collect(Collectors.toList());

        long totalAsistentes = asistenciasDelPeriodo.stream()
                .filter(a -> a.isAsistio())
                .count();

        float tasaAsistencia = asistenciasDelPeriodo.isEmpty() ? 0f : 
                (float) totalAsistentes / asistenciasDelPeriodo.size() * 100;

        var clase = asistencias.get(0).getClase();

        return List.of(new AsistenciaReporte(
                clase.getNombre(),
                clase.getInstructor().getUsuario().getNombreUsuario(),
                LocalDate.now(),
                (int) totalAsistentes,
                tasaAsistencia,
                tasaAsistencia
        ));
    }

    @Transactional(readOnly = true)
    public List<InstructorAsistenciaReporte> obtenerAsistenciaPorInstructor(Long instructorId) {
        log.info("Generando reporte de asistencia para instructor: {}", instructorId);

        var clases = claseRepositorio.findByInstructorId(instructorId);

        return clases.stream()
                .map(clase -> {
                    var asistencias = asistenciaRepositorio.findByClaseId(clase.getId());
                    long asistentes = asistencias.stream()
                            .filter(a -> a.isAsistio())
                            .count();
                    float tasaPromedio = asistencias.isEmpty() ? 0f : 
                            (float) asistentes / asistencias.size() * 100;

                    return new InstructorAsistenciaReporte(
                            clase.getInstructor().getUsuario().getNombreUsuario(),
                            clases.size(),
                            asistencias.size(),
                            tasaPromedio
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Float obtenerPromedioPorInstructor(Long instructorId) {
        log.info("Calculando promedio de asistencia para instructor: {}", instructorId);

        var reportes = obtenerAsistenciaPorInstructor(instructorId);

        if (reportes.isEmpty()) {
            return 0f;
        }

        return reportes.stream()
                .map(InstructorAsistenciaReporte::tasaPromedioPorClase)
                .reduce(0f, Float::sum) / reportes.size();
    }

    @Transactional(readOnly = true)
    public List<ClaseAsistenciaInfo> obtenerTasasAsistenciaPorClase() {
        log.info("Generando reporte de tasas de asistencia por clase");

        var clases = claseRepositorio.findAll();

        return clases.stream()
                .map(clase -> {
                    var asistencias = asistenciaRepositorio.findByClaseId(clase.getId());

                    if (asistencias.isEmpty()) {
                        return null;
                    }

                    long asistentes = asistencias.stream()
                            .filter(a -> a.isAsistio())
                            .count();

                    float tasaPromedio = (float) asistentes / asistencias.size() * 100;

                    return new ClaseAsistenciaInfo(
                            clase.getNombre(),
                            clase.getInstructor().getUsuario().getNombreUsuario(),
                            asistencias.size(),
                            tasaPromedio
                    );
                })
                .filter(c -> c != null)
                .collect(Collectors.toList());
    }
}
