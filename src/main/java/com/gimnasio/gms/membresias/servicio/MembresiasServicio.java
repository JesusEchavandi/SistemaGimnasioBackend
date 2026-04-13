package com.gimnasio.gms.membresias.servicio;

import com.gimnasio.gms.comun.excepcion.ExcepcionNegocio;
import com.gimnasio.gms.comun.excepcion.ExcepcionNoEncontrado;
import com.gimnasio.gms.membresias.dto.CrearMembresiasRequest;
import com.gimnasio.gms.membresias.dto.MembresiasAVencerResponse;
import com.gimnasio.gms.membresias.dto.MembresiasResponse;
import com.gimnasio.gms.membresias.entidad.EstadoMembresia;
import com.gimnasio.gms.membresias.entidad.Membresia;
import com.gimnasio.gms.membresias.repositorio.MembresiasRepositorio;
import com.gimnasio.gms.membresias.repositorio.MiembroRepositorio;
import com.gimnasio.gms.membresias.repositorio.PlanRepositorio;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MembresiasServicio {

    private final MembresiasRepositorio membresiasRepositorio;
    private final MiembroRepositorio miembroRepositorio;
    private final PlanRepositorio planRepositorio;

    @Transactional
    public MembresiasResponse crear(CrearMembresiasRequest request) {
        log.info("Creando membresía para miembro: {}, plan: {}", request.miembroId(), request.planId());

        var miembro = miembroRepositorio.findById(request.miembroId())
                .orElseThrow(() -> new ExcepcionNegocio("El miembro no existe"));

        var plan = planRepositorio.findById(request.planId())
                .orElseThrow(() -> new ExcepcionNegocio("El plan no existe"));

        if (!plan.isActivo()) {
            throw new ExcepcionNegocio("El plan no está activo");
        }

        Membresia membresia = new Membresia();
        membresia.setMiembro(miembro);
        membresia.setPlan(plan);
        membresia.setFechaInicio(request.fechaInicio());
        
        // Calcular fecha de vencimiento
        LocalDate fechaVencimiento = request.fechaInicio().plusDays(plan.getDuracionDias());
        membresia.setFechaVencimiento(fechaVencimiento);
        
        membresia.setEstado(EstadoMembresia.ACTIVA);

        Membresia guardada = membresiasRepositorio.save(membresia);
        log.info("Membresía creada exitosamente con id: {}", guardada.getId());

        return mapearResponse(guardada);
    }

    @Transactional
    public MembresiasResponse renovar(Long id) {
        log.info("Renovando membresía: {}", id);

        Membresia membresia = membresiasRepositorio.findById(id)
                .orElseThrow(() -> new ExcepcionNoEncontrado("Membresía no encontrada"));

        if (!membresia.getEstado().equals(EstadoMembresia.ACTIVA)) {
            throw new ExcepcionNegocio("Solo se pueden renovar membresías activas");
        }

        // Calcular nueva fecha de vencimiento
        LocalDate nuevoVencimiento = membresia.getFechaVencimiento().plusDays(membresia.getPlan().getDuracionDias());
        membresia.setFechaVencimiento(nuevoVencimiento);
        membresia.setFechaInicio(membresia.getFechaVencimiento().minusDays(membresia.getPlan().getDuracionDias()));

        Membresia renovada = membresiasRepositorio.save(membresia);
        log.info("Membresía renovada hasta: {}", renovada.getFechaVencimiento());

        return mapearResponse(renovada);
    }

    public MembresiasResponse obtener(Long id) {
        Membresia membresia = membresiasRepositorio.findById(id)
                .orElseThrow(() -> new ExcepcionNoEncontrado("Membresía no encontrada"));
        return mapearResponse(membresia);
    }

    public List<MembresiasResponse> listarPorMiembro(Long miembroId) {
        return membresiasRepositorio.findByMiembroId(miembroId).stream()
                .map(this::mapearResponse)
                .collect(Collectors.toList());
    }

    public List<MembresiasResponse> listarActivas() {
        return membresiasRepositorio.findByEstado(EstadoMembresia.ACTIVA).stream()
                .map(this::mapearResponse)
                .collect(Collectors.toList());
    }

    public List<MembresiasAVencerResponse> obtenerProximasAVencer(int diasRestantes) {
        LocalDate hoy = LocalDate.now();
        LocalDate proximosDias = hoy.plusDays(diasRestantes);

        return membresiasRepositorio.findMembresiasProximasAVencer(hoy, proximosDias).stream()
                .map(m -> new MembresiasAVencerResponse(
                        m.getId(),
                        m.getMiembro().getId(),
                        m.getMiembro().getUsuario().getNombreUsuario(),
                        m.getMiembro().getNumeroMembresia(),
                        m.getPlan().getNombre(),
                        m.getFechaVencimiento(),
                        (int) ChronoUnit.DAYS.between(hoy, m.getFechaVencimiento())
                ))
                .collect(Collectors.toList());
    }

    private MembresiasResponse mapearResponse(Membresia membresia) {
        LocalDate hoy = LocalDate.now();
        int diasRestantes = (int) ChronoUnit.DAYS.between(hoy, membresia.getFechaVencimiento());
        boolean conAlerta = diasRestantes <= 7 && diasRestantes > 0;

        return new MembresiasResponse(
                membresia.getId(),
                membresia.getMiembro().getId(),
                membresia.getMiembro().getUsuario().getNombreUsuario(),
                membresia.getPlan().getId(),
                membresia.getPlan().getNombre(),
                membresia.getFechaInicio(),
                membresia.getFechaVencimiento(),
                membresia.getEstado(),
                conAlerta,
                Math.max(diasRestantes, 0),
                membresia.getCreadoEn()
        );
    }
}
