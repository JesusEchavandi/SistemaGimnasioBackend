package com.gimnasio.gms.membresias.controlador;

import com.gimnasio.gms.comun.dto.RespuestaApi;
import com.gimnasio.gms.membresias.dto.CrearPlanRequest;
import com.gimnasio.gms.membresias.dto.PlanResponse;
import com.gimnasio.gms.membresias.servicio.PlanServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/v1/planes")
@RequiredArgsConstructor
@Tag(name = "Planes", description = "Gestión de planes de membresía")
public class PlanControlador {

    private final PlanServicio planServicio;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear nuevo plan", description = "Crea un nuevo plan de membresía")
    @ApiResponse(responseCode = "201", description = "Plan creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    public ResponseEntity<RespuestaApi<PlanResponse>> crear(@RequestBody @Valid CrearPlanRequest request) {
        log.info("POST /api/v1/planes - Crear plan");
        PlanResponse plan = planServicio.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RespuestaApi.ok("Plan creado exitosamente", plan));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar plan", description = "Actualiza un plan de membresía existente")
    @ApiResponse(responseCode = "200", description = "Plan actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Plan no encontrado")
    public ResponseEntity<RespuestaApi<PlanResponse>> actualizar(
            @PathVariable Long id,
            @RequestBody @Valid CrearPlanRequest request) {
        log.info("PUT /api/v1/planes/{} - Actualizar plan", id);
        PlanResponse plan = planServicio.actualizar(id, request);
        return ResponseEntity.ok(RespuestaApi.ok("Plan actualizado exitosamente", plan));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA', 'MIEMBRO')")
    @Operation(summary = "Obtener plan por ID", description = "Obtiene los detalles de un plan específico")
    @ApiResponse(responseCode = "200", description = "Plan encontrado")
    @ApiResponse(responseCode = "404", description = "Plan no encontrado")
    public ResponseEntity<RespuestaApi<PlanResponse>> obtener(@PathVariable Long id) {
        log.info("GET /api/v1/planes/{} - Obtener plan", id);
        PlanResponse plan = planServicio.obtener(id);
        return ResponseEntity.ok(RespuestaApi.ok("Plan obtenido exitosamente", plan));
    }

    @GetMapping("/activos")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA', 'MIEMBRO')")
    @Operation(summary = "Listar planes activos", description = "Obtiene la lista de planes activos disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de planes activos")
    public ResponseEntity<RespuestaApi<List<PlanResponse>>> listarActivos() {
        log.info("GET /api/v1/planes/activos - Listar planes activos");
        List<PlanResponse> planes = planServicio.listarActivos();
        return ResponseEntity.ok(RespuestaApi.ok("Total de planes activos: " + planes.size(), planes));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Listar todos los planes", description = "Obtiene la lista de todos los planes (activos e inactivos)")
    @ApiResponse(responseCode = "200", description = "Lista de planes")
    public ResponseEntity<RespuestaApi<List<PlanResponse>>> listar() {
        log.info("GET /api/v1/planes - Listar todos los planes");
        List<PlanResponse> planes = planServicio.listar();
        return ResponseEntity.ok(RespuestaApi.ok("Total de planes: " + planes.size(), planes));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Desactivar plan", description = "Desactiva un plan de membresía (soft delete)")
    @ApiResponse(responseCode = "200", description = "Plan desactivado exitosamente")
    @ApiResponse(responseCode = "404", description = "Plan no encontrado")
    public ResponseEntity<RespuestaApi<Void>> desactivar(@PathVariable Long id) {
        log.info("DELETE /api/v1/planes/{} - Desactivar plan", id);
        planServicio.desactivar(id);
        return ResponseEntity.ok(RespuestaApi.ok("Plan desactivado exitosamente", null));
    }
}
