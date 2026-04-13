package com.gimnasio.gms.clases.servicio;

import com.gimnasio.gms.clases.dto.AsistenciaResponse;
import com.gimnasio.gms.clases.dto.RegistrarAsistenciaRequest;
import com.gimnasio.gms.clases.entidad.Asistencia;
import com.gimnasio.gms.clases.repositorio.AsistenciaRepositorio;
import com.gimnasio.gms.clases.repositorio.ClaseRepositorio;
import com.gimnasio.gms.comun.excepcion.ExcepcionNegocio;
import com.gimnasio.gms.comun.excepcion.ExcepcionNoEncontrado;
import com.gimnasio.gms.membresias.repositorio.MiembroRepositorio;
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
public class AsistenciaServicio {

    private final AsistenciaRepositorio asistenciaRepositorio;
    private final ClaseRepositorio claseRepositorio;
    private final MiembroRepositorio miembroRepositorio;

    @Transactional
    public AsistenciaResponse registrar(RegistrarAsistenciaRequest request) {
        log.info("Registrando asistencia para clase: {}, miembro: {}", request.claseId(), request.miembroId());

        var clase = claseRepositorio.findById(request.claseId())
                .orElseThrow(() -> new ExcepcionNegocio("La clase no existe"));

        var miembro = miembroRepositorio.findById(request.miembroId())
                .orElseThrow(() -> new ExcepcionNegocio("El miembro no existe"));

        // Validar que no existe asistencia duplicada
        if (asistenciaRepositorio.findByClaseIdAndMiembroIdAndFecha(request.claseId(), request.miembroId(), request.fecha()).isPresent()) {
            throw new ExcepcionNegocio("Ya existe un registro de asistencia para esta clase y miembro en esta fecha");
        }

        Asistencia asistencia = new Asistencia();
        asistencia.setClase(clase);
        asistencia.setMiembro(miembro);
        asistencia.setFecha(request.fecha());
        asistencia.setAsistio(request.asistio());
        asistencia.setHoraLlegada(request.horaLlegada());

        Asistencia guardada = asistenciaRepositorio.save(asistencia);
        log.info("Asistencia registrada exitosamente con id: {}", guardada.getId());

        return mapearResponse(guardada);
    }

    @Transactional
    public AsistenciaResponse actualizar(Long id, RegistrarAsistenciaRequest request) {
        log.info("Actualizando asistencia con id: {}", id);

        Asistencia asistencia = asistenciaRepositorio.findById(id)
                .orElseThrow(() -> new ExcepcionNoEncontrado("Asistencia no encontrada"));

        asistencia.setAsistio(request.asistio());
        asistencia.setHoraLlegada(request.horaLlegada());

        Asistencia actualizada = asistenciaRepositorio.save(asistencia);
        log.info("Asistencia actualizada exitosamente");

        return mapearResponse(actualizada);
    }

    public AsistenciaResponse obtener(Long id) {
        Asistencia asistencia = asistenciaRepositorio.findById(id)
                .orElseThrow(() -> new ExcepcionNoEncontrado("Asistencia no encontrada"));
        return mapearResponse(asistencia);
    }

    public List<AsistenciaResponse> listarPorClase(Long claseId) {
        return asistenciaRepositorio.findByClaseId(claseId).stream()
                .map(this::mapearResponse)
                .collect(Collectors.toList());
    }

    public List<AsistenciaResponse> listarPorClaseYFecha(Long claseId, LocalDate fecha) {
        return asistenciaRepositorio.findByClaseIdAndFecha(claseId, fecha).stream()
                .map(this::mapearResponse)
                .collect(Collectors.toList());
    }

    public List<AsistenciaResponse> listarPorMiembro(Long miembroId) {
        return asistenciaRepositorio.findByMiembroId(miembroId).stream()
                .map(this::mapearResponse)
                .collect(Collectors.toList());
    }

    public Float calcularTasaAsistenciaPorClase(Long claseId) {
        var asistencias = asistenciaRepositorio.findByClaseId(claseId);
        if (asistencias.isEmpty()) {
            return 0f;
        }

        long asistentes = asistencias.stream().filter(Asistencia::isAsistio).count();
        return (float) asistentes / asistencias.size() * 100;
    }

    public Integer contarAsistenciasPorClase(Long claseId) {
        return asistenciaRepositorio.countAsistenciasEnClase(claseId);
    }

    private AsistenciaResponse mapearResponse(Asistencia asistencia) {
        return new AsistenciaResponse(
                asistencia.getId(),
                asistencia.getClase().getId(),
                asistencia.getClase().getNombre(),
                asistencia.getMiembro().getId(),
                asistencia.getMiembro().getUsuario().getNombreUsuario(),
                asistencia.getFecha(),
                asistencia.isAsistio(),
                asistencia.getHoraLlegada(),
                asistencia.getCreadoEn()
        );
    }
}
