package com.gimnasio.gms.membresias.servicio;

import com.gimnasio.gms.comun.excepcion.ExcepcionNegocio;
import com.gimnasio.gms.comun.excepcion.ExcepcionNoEncontrado;
import com.gimnasio.gms.membresias.dto.CrearPlanRequest;
import com.gimnasio.gms.membresias.dto.PlanResponse;
import com.gimnasio.gms.membresias.entidad.Plan;
import com.gimnasio.gms.membresias.repositorio.PlanRepositorio;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanServicio {

    private final PlanRepositorio planRepositorio;

    @Transactional
    public PlanResponse crear(CrearPlanRequest request) {
        log.info("Creando plan: {}", request.nombre());

        // Validar nombre único
        if (planRepositorio.existsByNombreAndActivo(request.nombre(), true)) {
            throw new ExcepcionNegocio("Ya existe un plan activo con este nombre");
        }

        // Validar precio
        if (request.precioMensual().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ExcepcionNegocio("El precio debe ser mayor a 0");
        }

        // Validar duración
        if (request.duracionDias() < 1) {
            throw new ExcepcionNegocio("La duración debe ser al menos 1 día");
        }

        Plan plan = new Plan();
        plan.setNombre(request.nombre());
        plan.setDescripcion(request.descripcion());
        plan.setPrecioMensual(request.precioMensual());
        plan.setDuracionDias(request.duracionDias());
        plan.setActivo(true);

        Plan guardado = planRepositorio.save(plan);
        log.info("Plan creado exitosamente con id: {}", guardado.getId());

        return mapearResponse(guardado);
    }

    @Transactional
    public PlanResponse actualizar(Long id, CrearPlanRequest request) {
        log.info("Actualizando plan con id: {}", id);

        Plan plan = planRepositorio.findById(id)
                .orElseThrow(() -> new ExcepcionNoEncontrado("Plan no encontrado"));

        if (!plan.getNombre().equals(request.nombre()) &&
            planRepositorio.existsByNombreAndActivo(request.nombre(), true)) {
            throw new ExcepcionNegocio("Ya existe un plan activo con este nombre");
        }

        if (request.precioMensual().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ExcepcionNegocio("El precio debe ser mayor a 0");
        }

        plan.setNombre(request.nombre());
        plan.setDescripcion(request.descripcion());
        plan.setPrecioMensual(request.precioMensual());
        plan.setDuracionDias(request.duracionDias());

        Plan actualizado = planRepositorio.save(plan);
        log.info("Plan actualizado exitosamente");

        return mapearResponse(actualizado);
    }

    public PlanResponse obtener(Long id) {
        Plan plan = planRepositorio.findById(id)
                .orElseThrow(() -> new ExcepcionNoEncontrado("Plan no encontrado"));
        return mapearResponse(plan);
    }

    public List<PlanResponse> listarActivos() {
        return planRepositorio.findByActivoTrue().stream()
                .map(this::mapearResponse)
                .collect(Collectors.toList());
    }

    public List<PlanResponse> listar() {
        return planRepositorio.findAll().stream()
                .map(this::mapearResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void desactivar(Long id) {
        Plan plan = planRepositorio.findById(id)
                .orElseThrow(() -> new ExcepcionNoEncontrado("Plan no encontrado"));
        plan.setActivo(false);
        planRepositorio.save(plan);
        log.info("Plan desactivado: {}", id);
    }

    private PlanResponse mapearResponse(Plan plan) {
        return new PlanResponse(
                plan.getId(),
                plan.getNombre(),
                plan.getDescripcion(),
                plan.getPrecioMensual(),
                plan.getDuracionDias(),
                plan.isActivo(),
                plan.getCreadoEn()
        );
    }
}
