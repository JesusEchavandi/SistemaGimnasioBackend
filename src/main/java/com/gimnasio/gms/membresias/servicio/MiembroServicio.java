package com.gimnasio.gms.membresias.servicio;

import com.gimnasio.gms.comun.excepcion.ExcepcionNegocio;
import com.gimnasio.gms.comun.excepcion.ExcepcionNoEncontrado;
import com.gimnasio.gms.membresias.dto.ActualizarMiembroRequest;
import com.gimnasio.gms.membresias.dto.CrearMiembroRequest;
import com.gimnasio.gms.membresias.dto.MiembroResponse;
import com.gimnasio.gms.membresias.entidad.Miembro;
import com.gimnasio.gms.membresias.repositorio.MiembroRepositorio;
import com.gimnasio.gms.seguridad.repositorio.UsuarioSistemaRepositorio;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MiembroServicio {

    private final MiembroRepositorio miembroRepositorio;
    private final UsuarioSistemaRepositorio usuarioRepositorio;

    @Transactional
    public MiembroResponse crear(CrearMiembroRequest request) {
        log.info("Creando miembro con numero: {}", request.numeroMembresia());

        // Validar que el usuario existe
        var usuario = usuarioRepositorio.findById(request.usuarioId())
                .orElseThrow(() -> new ExcepcionNegocio("El usuario no existe"));

        // Validar que no existe miembro con este usuario
        if (miembroRepositorio.existsByUsuarioId(request.usuarioId())) {
            throw new ExcepcionNegocio("El usuario ya tiene un miembro registrado");
        }

        // Validar número de membresía único
        if (miembroRepositorio.existsByNumeroMembresia(request.numeroMembresia())) {
            throw new ExcepcionNegocio("El número de membresía ya existe");
        }

        Miembro miembro = new Miembro();
        miembro.setUsuario(usuario);
        miembro.setNumeroMembresia(request.numeroMembresia());
        miembro.setTelefonoContacto(request.telefonoContacto());
        miembro.setFechaNacimiento(request.fechaNacimiento());
        miembro.setSexo(request.sexo());
        miembro.setTelefonoEmergencia(request.telefonoEmergencia());

        Miembro guardado = miembroRepositorio.save(miembro);
        log.info("Miembro creado exitosamente con id: {}", guardado.getId());

        return mapearResponse(guardado);
    }

    @Transactional
    public MiembroResponse actualizar(Long id, ActualizarMiembroRequest request) {
        log.info("Actualizando miembro con id: {}", id);

        Miembro miembro = miembroRepositorio.findById(id)
                .orElseThrow(() -> new ExcepcionNoEncontrado("Miembro no encontrado"));

        if (request.numeroMembresia() != null && !request.numeroMembresia().equals(miembro.getNumeroMembresia())) {
            if (miembroRepositorio.existsByNumeroMembresia(request.numeroMembresia())) {
                throw new ExcepcionNegocio("El número de membresía ya existe");
            }
            miembro.setNumeroMembresia(request.numeroMembresia());
        }

        if (request.telefonoContacto() != null) {
            miembro.setTelefonoContacto(request.telefonoContacto());
        }

        if (request.telefonoEmergencia() != null) {
            miembro.setTelefonoEmergencia(request.telefonoEmergencia());
        }

        Miembro actualizado = miembroRepositorio.save(miembro);
        log.info("Miembro actualizado exitosamente");

        return mapearResponse(actualizado);
    }

    public MiembroResponse obtener(Long id) {
        Miembro miembro = miembroRepositorio.findById(id)
                .orElseThrow(() -> new ExcepcionNoEncontrado("Miembro no encontrado"));
        return mapearResponse(miembro);
    }

    public MiembroResponse obtenerPorUsuario(Long usuarioId) {
        Miembro miembro = miembroRepositorio.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ExcepcionNoEncontrado("Miembro no encontrado para este usuario"));
        return mapearResponse(miembro);
    }

    public List<MiembroResponse> listar() {
        return miembroRepositorio.findAll().stream()
                .map(this::mapearResponse)
                .collect(Collectors.toList());
    }

    private MiembroResponse mapearResponse(Miembro miembro) {
        return new MiembroResponse(
                miembro.getId(),
                miembro.getUsuario().getId(),
                miembro.getUsuario().getNombreUsuario(),
                miembro.getNumeroMembresia(),
                miembro.getTelefonoContacto(),
                miembro.getFechaNacimiento(),
                miembro.getSexo(),
                miembro.getTelefonoEmergencia(),
                miembro.getCreadoEn(),
                miembro.getActualizadoEn()
        );
    }
}
