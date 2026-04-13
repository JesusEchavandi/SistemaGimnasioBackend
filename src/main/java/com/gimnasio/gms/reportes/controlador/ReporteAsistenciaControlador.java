package com.gimnasio.gms.reportes.controlador;

import com.gimnasio.gms.comun.dto.RespuestaApi;
import com.gimnasio.gms.reportes.dto.AsistenciaReporte;
import com.gimnasio.gms.reportes.dto.ClaseAsistenciaInfo;
import com.gimnasio.gms.reportes.dto.InstructorAsistenciaReporte;
import com.gimnasio.gms.reportes.servicio.ReporteAsistenciaServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/reportes/asistencias")
@RequiredArgsConstructor
@Tag(name = "Reportes de Asistencias", description = "Reportes e informes sobre asistencias a clases")
public class ReporteAsistenciaControlador {

    private final ReporteAsistenciaServicio reporteAsistenciaServicio;

    @GetMapping("/clase/{claseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Asistencias por clase", description = "Obtiene el reporte de asistencias de una clase específica")
    @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente")
    public ResponseEntity<RespuestaApi<List<ClaseAsistenciaInfo>>> obtenerAsistenciasPorClase(@PathVariable Long claseId) {
        log.info("GET /api/v1/reportes/asistencias/clase/{} - Por clase", claseId);
        List<ClaseAsistenciaInfo> reportes = reporteAsistenciaServicio.obtenerAsistenciasPorClase(claseId);
        return ResponseEntity.ok(RespuestaApi.ok("Asistencias de la clase: " + reportes.size(), reportes));
    }

    @GetMapping("/clase/{claseId}/periodo")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Asistencias por período", description = "Obtiene las asistencias de una clase en un período específico")
    @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente")
    public ResponseEntity<RespuestaApi<List<AsistenciaReporte>>> obtenerAsistenciaPorClaseYPeriodo(
            @PathVariable Long claseId,
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin) {
        log.info("GET /api/v1/reportes/asistencias/clase/{}/periodo - Período {} a {}", claseId, fechaInicio, fechaFin);
        List<AsistenciaReporte> reportes = reporteAsistenciaServicio.obtenerAsistenciaPorClaseYPeriodo(claseId, fechaInicio, fechaFin);
        return ResponseEntity.ok(RespuestaApi.ok("Asistencias en el período: " + reportes.size(), reportes));
    }

    @GetMapping("/instructor/{instructorId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Asistencias por instructor", description = "Obtiene el reporte de asistencias de todas las clases de un instructor")
    @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente")
    public ResponseEntity<RespuestaApi<List<InstructorAsistenciaReporte>>> obtenerAsistenciaPorInstructor(@PathVariable Long instructorId) {
        log.info("GET /api/v1/reportes/asistencias/instructor/{} - Por instructor", instructorId);
        List<InstructorAsistenciaReporte> reportes = reporteAsistenciaServicio.obtenerAsistenciaPorInstructor(instructorId);
        return ResponseEntity.ok(RespuestaApi.ok("Total de clases: " + reportes.size(), reportes));
    }

    @GetMapping("/instructor/{instructorId}/promedio")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Promedio de asistencia por instructor", description = "Calcula el promedio de asistencia de todas las clases de un instructor")
    @ApiResponse(responseCode = "200", description = "Promedio calculado")
    public ResponseEntity<RespuestaApi<Float>> obtenerPromedioPorInstructor(@PathVariable Long instructorId) {
        log.info("GET /api/v1/reportes/asistencias/instructor/{}/promedio - Promedio", instructorId);
        Float promedio = reporteAsistenciaServicio.obtenerPromedioPorInstructor(instructorId);
        return ResponseEntity.ok(RespuestaApi.ok(String.format("Promedio de asistencia: %.2f%%", promedio), promedio));
    }

    @GetMapping("/tasas-por-clase")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Tasas de asistencia por clase", description = "Obtiene el resumen de tasas de asistencia de todas las clases")
    @ApiResponse(responseCode = "200", description = "Tasas calculadas")
    public ResponseEntity<RespuestaApi<List<ClaseAsistenciaInfo>>> obtenerTasasAsistenciaPorClase() {
        log.info("GET /api/v1/reportes/asistencias/tasas-por-clase - Tasas por clase");
        List<ClaseAsistenciaInfo> tasas = reporteAsistenciaServicio.obtenerTasasAsistenciaPorClase();
        return ResponseEntity.ok(RespuestaApi.ok("Total de clases: " + tasas.size(), tasas));
    }
}
