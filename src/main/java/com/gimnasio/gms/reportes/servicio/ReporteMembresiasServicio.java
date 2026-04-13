package com.gimnasio.gms.reportes.servicio;

import com.gimnasio.gms.reportes.dto.MembresiaReporte;
import com.gimnasio.gms.reportes.dto.PlanEstadistica;
import com.gimnasio.gms.membresias.repositorio.MembresiasRepositorio;
import com.gimnasio.gms.membresias.repositorio.PlanRepositorio;
import com.gimnasio.gms.membresias.entidad.EstadoMembresia;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReporteMembresiasServicio {

    private final MembresiasRepositorio membresiasRepositorio;
    private final PlanRepositorio planRepositorio;

    @Transactional(readOnly = true)
    public List<MembresiaReporte> obtenerReportePorPlan() {
        log.info("Generando reporte de membresías por plan");

        var todosLosPlanes = planRepositorio.findAll();

        return todosLosPlanes.stream()
                .map(plan -> {
                    var membresias = membresiasRepositorio.findAll();
                    var membresiasDelPlan = membresias.stream()
                            .filter(m -> m.getPlan().getId().equals(plan.getId()))
                            .collect(Collectors.toList());

                    long activas = membresiasDelPlan.stream()
                            .filter(m -> m.getEstado() == EstadoMembresia.ACTIVA)
                            .count();
                    long vencidas = membresiasDelPlan.stream()
                            .filter(m -> m.getEstado() == EstadoMembresia.VENCIDA)
                            .count();

                    BigDecimal ingresoMes = plan.getPrecioMensual().multiply(BigDecimal.valueOf(activas));

                    return new MembresiaReporte(
                            plan.getNombre(),
                            (int) activas,
                            (int) vencidas,
                            ingresoMes
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PlanEstadistica> obtenerEstadisticasPorPlan() {
        log.info("Generando estadísticas por plan");

        var planes = planRepositorio.findByActivoTrue();

        return planes.stream()
                .map(plan -> {
                    var membresias = membresiasRepositorio.findAll();
                    var membresiasDelPlan = membresias.stream()
                            .filter(m -> m.getPlan().getId().equals(plan.getId()))
                            .collect(Collectors.toList());

                    BigDecimal ingreso = plan.getPrecioMensual().multiply(BigDecimal.valueOf(membresiasDelPlan.size()));

                    return new PlanEstadistica(
                            plan.getNombre(),
                            membresiasDelPlan.size(),
                            ingreso
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Map<EstadoMembresia, Long> obtenerCantidadPorEstado() {
        log.info("Obteniendo cantidades por estado de membresía");

        var membresias = membresiasRepositorio.findAll();

        return membresias.stream()
                .collect(Collectors.groupingBy(
                        m -> m.getEstado(),
                        Collectors.counting()
                ));
    }

    @Transactional(readOnly = true)
    public Long contarMembresiasActivas() {
        log.info("Contando membresías activas");
        return (long) membresiasRepositorio.findByEstado(EstadoMembresia.ACTIVA).size();
    }

    @Transactional(readOnly = true)
    public Long contarMembresiasVencidas() {
        log.info("Contando membresías vencidas");
        return (long) membresiasRepositorio.findByEstado(EstadoMembresia.VENCIDA).size();
    }

    private int calcularDiasRestantes(LocalDate fechaVencimiento) {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), fechaVencimiento);
    }
}
