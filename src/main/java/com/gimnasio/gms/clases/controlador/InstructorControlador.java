package com.gimnasio.gms.clases.controlador;

import com.gimnasio.gms.comun.dto.RespuestaApi;
import com.gimnasio.gms.clases.dto.CrearInstructorRequest;
import com.gimnasio.gms.clases.dto.ActualizarInstructorRequest;
import com.gimnasio.gms.clases.dto.InstructorResponse;
import com.gimnasio.gms.clases.servicio.InstructorServicio;
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
@RequestMapping("/api/v1/instructores")
@RequiredArgsConstructor
@Tag(name = "Instructores", description = "Gestión de instructores de clases")
public class InstructorControlador {

    private final InstructorServicio instructorServicio;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear nuevo instructor", description = "Crea un nuevo instructor en el sistema")
    @ApiResponse(responseCode = "201", description = "Instructor creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    public ResponseEntity<RespuestaApi<InstructorResponse>> crear(@RequestBody @Valid CrearInstructorRequest request) {
        log.info("POST /api/v1/instructores - Crear instructor");
        InstructorResponse instructor = instructorServicio.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RespuestaApi.ok("Instructor creado exitosamente", instructor));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar instructor", description = "Actualiza los datos de un instructor existente")
    @ApiResponse(responseCode = "200", description = "Instructor actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Instructor no encontrado")
    public ResponseEntity<RespuestaApi<InstructorResponse>> actualizar(
            @PathVariable Long id,
            @RequestBody @Valid ActualizarInstructorRequest request) {
        log.info("PUT /api/v1/instructores/{} - Actualizar instructor", id);
        InstructorResponse instructor = instructorServicio.actualizar(id, request);
        return ResponseEntity.ok(RespuestaApi.ok("Instructor actualizado exitosamente", instructor));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Obtener instructor por ID", description = "Obtiene los detalles de un instructor específico")
    @ApiResponse(responseCode = "200", description = "Instructor encontrado")
    @ApiResponse(responseCode = "404", description = "Instructor no encontrado")
    public ResponseEntity<RespuestaApi<InstructorResponse>> obtener(@PathVariable Long id) {
        log.info("GET /api/v1/instructores/{} - Obtener instructor", id);
        InstructorResponse instructor = instructorServicio.obtener(id);
        return ResponseEntity.ok(RespuestaApi.ok("Instructor obtenido exitosamente", instructor));
    }

    @GetMapping("/activos")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA', 'MIEMBRO')")
    @Operation(summary = "Listar instructores activos", description = "Obtiene la lista de instructores activos")
    @ApiResponse(responseCode = "200", description = "Lista de instructores activos")
    public ResponseEntity<RespuestaApi<List<InstructorResponse>>> listarActivos() {
        log.info("GET /api/v1/instructores/activos - Listar instructores activos");
        List<InstructorResponse> instructores = instructorServicio.listarActivos();
        return ResponseEntity.ok(RespuestaApi.ok("Total de instructores activos: " + instructores.size(), instructores));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Listar todos los instructores", description = "Obtiene la lista de todos los instructores")
    @ApiResponse(responseCode = "200", description = "Lista de instructores")
    public ResponseEntity<RespuestaApi<List<InstructorResponse>>> listar() {
        log.info("GET /api/v1/instructores - Listar todos los instructores");
        List<InstructorResponse> instructores = instructorServicio.listar();
        return ResponseEntity.ok(RespuestaApi.ok("Total de instructores: " + instructores.size(), instructores));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Desactivar instructor", description = "Desactiva un instructor (soft delete)")
    @ApiResponse(responseCode = "200", description = "Instructor desactivado exitosamente")
    @ApiResponse(responseCode = "404", description = "Instructor no encontrado")
    public ResponseEntity<RespuestaApi<Void>> desactivar(@PathVariable Long id) {
        log.info("DELETE /api/v1/instructores/{} - Desactivar instructor", id);
        instructorServicio.desactivar(id);
        return ResponseEntity.ok(RespuestaApi.ok("Instructor desactivado exitosamente", null));
    }
}
