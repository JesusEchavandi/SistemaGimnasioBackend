package com.gimnasio.gms.clases.servicio;

import com.gimnasio.gms.clases.dto.ActualizarInstructorRequest;
import com.gimnasio.gms.clases.dto.CrearInstructorRequest;
import com.gimnasio.gms.clases.dto.InstructorResponse;
import com.gimnasio.gms.comun.excepcion.ExcepcionNegocio;
import com.gimnasio.gms.comun.excepcion.ExcepcionNoEncontrado;
import com.gimnasio.gms.clases.entidad.Instructor;
import com.gimnasio.gms.clases.repositorio.InstructorRepositorio;
import com.gimnasio.gms.seguridad.repositorio.UsuarioSistemaRepositorio;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstructorServicio {

    private final InstructorRepositorio instructorRepositorio;
    private final UsuarioSistemaRepositorio usuarioRepositorio;

    @Transactional
    public InstructorResponse crear(CrearInstructorRequest request) {
        log.info("Creando instructor con usuario: {}", request.usuarioId());

        var usuario = usuarioRepositorio.findById(request.usuarioId())
                .orElseThrow(() -> new ExcepcionNegocio("El usuario no existe"));

        if (instructorRepositorio.existsByUsuarioId(request.usuarioId())) {
            throw new ExcepcionNegocio("Este usuario ya es instructor");
        }

        Instructor instructor = new Instructor();
        instructor.setUsuario(usuario);
        instructor.setEspecialidad(request.especialidad());
        instructor.setCertificaciones(request.certificaciones());
        instructor.setActivo(true);

        Instructor guardado = instructorRepositorio.save(instructor);
        log.info("Instructor creado exitosamente con id: {}", guardado.getId());

        return mapearResponse(guardado);
    }

    @Transactional
    public InstructorResponse actualizar(Long id, ActualizarInstructorRequest request) {
        log.info("Actualizando instructor con id: {}", id);

        Instructor instructor = instructorRepositorio.findById(id)
                .orElseThrow(() -> new ExcepcionNoEncontrado("Instructor no encontrado"));

        if (request.especialidad() != null) {
            instructor.setEspecialidad(request.especialidad());
        }

        if (request.certificaciones() != null) {
            instructor.setCertificaciones(request.certificaciones());
        }

        Instructor actualizado = instructorRepositorio.save(instructor);
        log.info("Instructor actualizado exitosamente");

        return mapearResponse(actualizado);
    }

    public InstructorResponse obtener(Long id) {
        Instructor instructor = instructorRepositorio.findById(id)
                .orElseThrow(() -> new ExcepcionNoEncontrado("Instructor no encontrado"));
        return mapearResponse(instructor);
    }

    public List<InstructorResponse> listarActivos() {
        return instructorRepositorio.findByActivoTrue().stream()
                .map(this::mapearResponse)
                .collect(Collectors.toList());
    }

    public List<InstructorResponse> listar() {
        return instructorRepositorio.findAll().stream()
                .map(this::mapearResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void desactivar(Long id) {
        Instructor instructor = instructorRepositorio.findById(id)
                .orElseThrow(() -> new ExcepcionNoEncontrado("Instructor no encontrado"));
        instructor.setActivo(false);
        instructorRepositorio.save(instructor);
        log.info("Instructor desactivado: {}", id);
    }

    private InstructorResponse mapearResponse(Instructor instructor) {
        return new InstructorResponse(
                instructor.getId(),
                instructor.getUsuario().getId(),
                instructor.getUsuario().getNombreUsuario(),
                instructor.getEspecialidad(),
                instructor.getCertificaciones(),
                instructor.isActivo(),
                instructor.getCreadoEn()
        );
    }
}
