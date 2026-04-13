package com.gimnasio.gms.reportes.controlador;

import com.gimnasio.gms.comun.dto.RespuestaApi;
import com.gimnasio.gms.reportes.dto.MembresiaReporte;
import com.gimnasio.gms.reportes.dto.PlanEstadistica;
import com.gimnasio.gms.reportes.servicio.ReporteMembresiasServicio;
import com.gimnasio.gms.membresias.entidad.EstadoMembresia;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/reportes/membresias")
@RequiredArgsConstructor
@Tag(name = "Reportes de Membresías", description = "Reportes e informes sobre membresías")
public class ReporteMembresiasControlador {

    private final ReporteMembresiasServicio reporteMembresiasServicio;

    @GetMapping("/por-plan")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Reporte de membresías por plan", description = "Obtiene un reporte de membresías agrupadas por plan")
    @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente")
    public ResponseEntity<RespuestaApi<List<MembresiaReporte>>> obtenerReportePorPlan() {
        log.info("GET /api/v1/reportes/membresias/por-plan - Reporte por plan");
        List<MembresiaReporte> reportes = reporteMembresiasServicio.obtenerReportePorPlan();
        return ResponseEntity.ok(RespuestaApi.ok("Reporte de membresías por plan generado", reportes));
    }

    @GetMapping("/estadisticas")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Estadísticas de planes", description = "Obtiene estadísticas detalladas de membresías por cada plan")
    @ApiResponse(responseCode = "200", description = "Estadísticas generadas exitosamente")
    public ResponseEntity<RespuestaApi<List<PlanEstadistica>>> obtenerEstadisticasPorPlan() {
        log.info("GET /api/v1/reportes/membresias/estadisticas - Estadísticas por plan");
        List<PlanEstadistica> estadisticas = reporteMembresiasServicio.obtenerEstadisticasPorPlan();
        return ResponseEntity.ok(RespuestaApi.ok("Estadísticas de planes generadas", estadisticas));
    }

    @GetMapping("/por-estado")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Membresías por estado", description = "Obtiene el conteo de membresías agrupadas por estado (ACTIVA, VENCIDA, CANCELADA)")
    @ApiResponse(responseCode = "200", description = "Conteos generados exitosamente")
    public ResponseEntity<RespuestaApi<Map<EstadoMembresia, Long>>> obtenerCantidadPorEstado() {
        log.info("GET /api/v1/reportes/membresias/por-estado - Conteo por estado");
        Map<EstadoMembresia, Long> conteos = reporteMembresiasServicio.obtenerCantidadPorEstado();
        return ResponseEntity.ok(RespuestaApi.ok("Conteos de membresías por estado", conteos));
    }

    @GetMapping("/activas/total")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Total de membresías activas", description = "Obtiene el total de membresías activas en el sistema")
    @ApiResponse(responseCode = "200", description = "Total calculado")
    public ResponseEntity<RespuestaApi<Long>> contarMembresiasActivas() {
        log.info("GET /api/v1/reportes/membresias/activas/total - Total activas");
        Long total = reporteMembresiasServicio.contarMembresiasActivas();
        return ResponseEntity.ok(RespuestaApi.ok("Total de membresías activas: " + total, total));
    }

    @GetMapping("/vencidas/total")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Total de membresías vencidas", description = "Obtiene el total de membresías vencidas en el sistema")
    @ApiResponse(responseCode = "200", description = "Total calculado")
    public ResponseEntity<RespuestaApi<Long>> contarMembresiasVencidas() {
        log.info("GET /api/v1/reportes/membresias/vencidas/total - Total vencidas");
        Long total = reporteMembresiasServicio.contarMembresiasVencidas();
        return ResponseEntity.ok(RespuestaApi.ok("Total de membresías vencidas: " + total, total));
    }
}
