package com.gimnasio.gms.clases.controlador;

import com.gimnasio.gms.comun.dto.RespuestaApi;
import com.gimnasio.gms.clases.dto.RegistrarAsistenciaRequest;
import com.gimnasio.gms.clases.dto.AsistenciaResponse;
import com.gimnasio.gms.clases.servicio.AsistenciaServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/asistencias")
@RequiredArgsConstructor
@Tag(name = "Asistencias", description = "Gestión de asistencias a clases")
public class AsistenciaControlador {

    private final AsistenciaServicio asistenciaServicio;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Registrar asistencia", description = "Registra la asistencia de un miembro a una clase")
    @ApiResponse(responseCode = "201", description = "Asistencia registrada exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    public ResponseEntity<RespuestaApi<AsistenciaResponse>> registrar(@RequestBody @Valid RegistrarAsistenciaRequest request) {
        log.info("POST /api/v1/asistencias - Registrar asistencia");
        AsistenciaResponse asistencia = asistenciaServicio.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RespuestaApi.ok("Asistencia registrada exitosamente", asistencia));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Actualizar asistencia", description = "Actualiza el registro de asistencia de un miembro")
    @ApiResponse(responseCode = "200", description = "Asistencia actualizada exitosamente")
    @ApiResponse(responseCode = "404", description = "Asistencia no encontrada")
    public ResponseEntity<RespuestaApi<AsistenciaResponse>> actualizar(
            @PathVariable Long id,
            @RequestBody @Valid RegistrarAsistenciaRequest request) {
        log.info("PUT /api/v1/asistencias/{} - Actualizar asistencia", id);
        AsistenciaResponse asistencia = asistenciaServicio.actualizar(id, request);
        return ResponseEntity.ok(RespuestaApi.ok("Asistencia actualizada exitosamente", asistencia));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Obtener asistencia por ID", description = "Obtiene los detalles de un registro de asistencia específico")
    @ApiResponse(responseCode = "200", description = "Asistencia encontrada")
    @ApiResponse(responseCode = "404", description = "Asistencia no encontrada")
    public ResponseEntity<RespuestaApi<AsistenciaResponse>> obtener(@PathVariable Long id) {
        log.info("GET /api/v1/asistencias/{} - Obtener asistencia", id);
        AsistenciaResponse asistencia = asistenciaServicio.obtener(id);
        return ResponseEntity.ok(RespuestaApi.ok("Asistencia obtenida exitosamente", asistencia));
    }

    @GetMapping("/clase/{claseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Listar asistencias por clase", description = "Obtiene todos los registros de asistencia de una clase")
    @ApiResponse(responseCode = "200", description = "Lista de asistencias")
    public ResponseEntity<RespuestaApi<List<AsistenciaResponse>>> listarPorClase(@PathVariable Long claseId) {
        log.info("GET /api/v1/asistencias/clase/{} - Listar por clase", claseId);
        List<AsistenciaResponse> asistencias = asistenciaServicio.listarPorClase(claseId);
        return ResponseEntity.ok(RespuestaApi.ok("Total de registros: " + asistencias.size(), asistencias));
    }

    @GetMapping("/miembro/{miembroId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA', 'MIEMBRO')")
    @Operation(summary = "Listar asistencias por miembro", description = "Obtiene todos los registros de asistencia de un miembro")
    @ApiResponse(responseCode = "200", description = "Lista de asistencias del miembro")
    public ResponseEntity<RespuestaApi<List<AsistenciaResponse>>> listarPorMiembro(@PathVariable Long miembroId) {
        log.info("GET /api/v1/asistencias/miembro/{} - Listar por miembro", miembroId);
        List<AsistenciaResponse> asistencias = asistenciaServicio.listarPorMiembro(miembroId);
        return ResponseEntity.ok(RespuestaApi.ok("Total de asistencias: " + asistencias.size(), asistencias));
    }

    @GetMapping("/clase/{claseId}/tasa-asistencia")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Calcular tasa de asistencia", description = "Calcula el porcentaje de asistencia para una clase")
    @ApiResponse(responseCode = "200", description = "Tasa calculada")
    public ResponseEntity<RespuestaApi<Float>> calcularTasaAsistencia(@PathVariable Long claseId) {
        log.info("GET /api/v1/asistencias/{}/tasa-asistencia - Calcular tasa", claseId);
        Float tasa = asistenciaServicio.calcularTasaAsistenciaPorClase(claseId);
        return ResponseEntity.ok(RespuestaApi.ok(String.format("Tasa de asistencia: %.2f%%", tasa), tasa));
    }
}
