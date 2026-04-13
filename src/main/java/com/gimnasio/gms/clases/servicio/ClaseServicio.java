package com.gimnasio.gms.clases.servicio;

import com.gimnasio.gms.clases.dto.ActualizarClaseRequest;
import com.gimnasio.gms.clases.dto.CrearClaseRequest;
import com.gimnasio.gms.clases.dto.ClaseResponse;
import com.gimnasio.gms.clases.entidad.Clase;
import com.gimnasio.gms.clases.entidad.DiaSemana;
import com.gimnasio.gms.clases.entidad.EstadoClase;
import com.gimnasio.gms.clases.repositorio.ClaseRepositorio;
import com.gimnasio.gms.clases.repositorio.InstructorRepositorio;
import com.gimnasio.gms.comun.excepcion.ExcepcionNegocio;
import com.gimnasio.gms.comun.excepcion.ExcepcionNoEncontrado;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClaseServicio {

    private final ClaseRepositorio claseRepositorio;
    private final InstructorRepositorio instructorRepositorio;

    @Transactional
    public ClaseResponse crear(CrearClaseRequest request) {
        log.info("Creando clase: {}", request.nombre());

        var instructor = instructorRepositorio.findById(request.instructorId())
                .orElseThrow(() -> new ExcepcionNegocio("El instructor no existe"));

        if (!instructor.isActivo()) {
            throw new ExcepcionNegocio("El instructor no está activo");
        }

        if (request.duracionMinutos() < 15) {
            throw new ExcepcionNegocio("La duración mínima debe ser 15 minutos");
        }

        if (request.capacidad() < 1) {
            throw new ExcepcionNegocio("La capacidad debe ser mayor a 0");
        }

        Clase clase = new Clase();
        clase.setNombre(request.nombre());
        clase.setInstructor(instructor);
        clase.setDiaSemana(request.diaSemana());
        clase.setHoraInicio(request.horaInicio());
        clase.setDuracionMinutos(request.duracionMinutos());
        clase.setCapacidad(request.capacidad());
        clase.setEstado(EstadoClase.ACTIVA);

        Clase guardada = claseRepositorio.save(clase);
        log.info("Clase creada exitosamente con id: {}", guardada.getId());

        return mapearResponse(guardada);
    }

    @Transactional
    public ClaseResponse actualizar(Long id, ActualizarClaseRequest request) {
        log.info("Actualizando clase con id: {}", id);

        Clase clase = claseRepositorio.findById(id)
                .orElseThrow(() -> new ExcepcionNoEncontrado("Clase no encontrada"));

        if (request.nombre() != null) {
            clase.setNombre(request.nombre());
        }

        if (request.diaSemana() != null) {
            clase.setDiaSemana(request.diaSemana());
        }

        if (request.horaInicio() != null) {
            clase.setHoraInicio(request.horaInicio());
        }

        if (request.duracionMinutos() != null) {
            if (request.duracionMinutos() < 15) {
                throw new ExcepcionNegocio("La duración mínima debe ser 15 minutos");
            }
            clase.setDuracionMinutos(request.duracionMinutos());
        }

        if (request.capacidad() != null) {
            if (request.capacidad() < 1) {
                throw new ExcepcionNegocio("La capacidad debe ser mayor a 0");
            }
            clase.setCapacidad(request.capacidad());
        }

        Clase actualizada = claseRepositorio.save(clase);
        log.info("Clase actualizada exitosamente");

        return mapearResponse(actualizada);
    }

    public ClaseResponse obtener(Long id) {
        Clase clase = claseRepositorio.findById(id)
                .orElseThrow(() -> new ExcepcionNoEncontrado("Clase no encontrada"));
        return mapearResponse(clase);
    }

    public List<ClaseResponse> listarPorInstructor(Long instructorId) {
        return claseRepositorio.findByInstructorId(instructorId).stream()
                .map(this::mapearResponse)
                .collect(Collectors.toList());
    }

    public List<ClaseResponse> listarPorDia(DiaSemana diaSemana) {
        return claseRepositorio.findByDiaSemana(diaSemana).stream()
                .map(this::mapearResponse)
                .collect(Collectors.toList());
    }

    public List<ClaseResponse> listarPorInstructorYDia(Long instructorId, DiaSemana diaSemana) {
        return claseRepositorio.findByInstructorIdAndDiaSemana(instructorId, diaSemana).stream()
                .map(this::mapearResponse)
                .collect(Collectors.toList());
    }

    public List<ClaseResponse> listar() {
        return claseRepositorio.findByEstado(EstadoClase.ACTIVA).stream()
                .map(this::mapearResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelar(Long id) {
        Clase clase = claseRepositorio.findById(id)
                .orElseThrow(() -> new ExcepcionNoEncontrado("Clase no encontrada"));
        clase.setEstado(EstadoClase.CANCELADA);
        claseRepositorio.save(clase);
        log.info("Clase cancelada: {}", id);
    }

    private ClaseResponse mapearResponse(Clase clase) {
        return new ClaseResponse(
                clase.getId(),
                clase.getNombre(),
                clase.getInstructor().getId(),
                clase.getInstructor().getUsuario().getNombreUsuario(),
                clase.getDiaSemana(),
                clase.getHoraInicio(),
                clase.getDuracionMinutos(),
                clase.getCapacidad(),
                clase.getEstado(),
                clase.getCreadoEn()
        );
    }
}
