package com.gimnasio.gms.clases.controlador;

import com.gimnasio.gms.comun.dto.RespuestaApi;
import com.gimnasio.gms.clases.dto.CrearClaseRequest;
import com.gimnasio.gms.clases.dto.ActualizarClaseRequest;
import com.gimnasio.gms.clases.dto.ClaseResponse;
import com.gimnasio.gms.clases.entidad.DiaSemana;
import com.gimnasio.gms.clases.servicio.ClaseServicio;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/clases")
@RequiredArgsConstructor
@Tag(name = "Clases", description = "Gestión de clases del gimnasio")
public class ClaseControlador {

    private final ClaseServicio claseServicio;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Crear nueva clase", description = "Crea una nueva clase de gimnasia")
    @ApiResponse(responseCode = "201", description = "Clase creada exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    public ResponseEntity<RespuestaApi<ClaseResponse>> crear(@RequestBody @Valid CrearClaseRequest request) {
        log.info("POST /api/v1/clases - Crear clase");
        ClaseResponse clase = claseServicio.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RespuestaApi.ok("Clase creada exitosamente", clase));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Actualizar clase", description = "Actualiza los datos de una clase existente")
    @ApiResponse(responseCode = "200", description = "Clase actualizada exitosamente")
    @ApiResponse(responseCode = "404", description = "Clase no encontrada")
    public ResponseEntity<RespuestaApi<ClaseResponse>> actualizar(
            @PathVariable Long id,
            @RequestBody @Valid ActualizarClaseRequest request) {
        log.info("PUT /api/v1/clases/{} - Actualizar clase", id);
        ClaseResponse clase = claseServicio.actualizar(id, request);
        return ResponseEntity.ok(RespuestaApi.ok("Clase actualizada exitosamente", clase));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA', 'MIEMBRO')")
    @Operation(summary = "Obtener clase por ID", description = "Obtiene los detalles de una clase específica")
    @ApiResponse(responseCode = "200", description = "Clase encontrada")
    @ApiResponse(responseCode = "404", description = "Clase no encontrada")
    public ResponseEntity<RespuestaApi<ClaseResponse>> obtener(@PathVariable Long id) {
        log.info("GET /api/v1/clases/{} - Obtener clase", id);
        ClaseResponse clase = claseServicio.obtener(id);
        return ResponseEntity.ok(RespuestaApi.ok("Clase obtenida exitosamente", clase));
    }

    @GetMapping("/instructor/{instructorId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA', 'MIEMBRO')")
    @Operation(summary = "Listar clases por instructor", description = "Obtiene todas las clases de un instructor específico")
    @ApiResponse(responseCode = "200", description = "Lista de clases del instructor")
    public ResponseEntity<RespuestaApi<List<ClaseResponse>>> listarPorInstructor(@PathVariable Long instructorId) {
        log.info("GET /api/v1/clases/instructor/{} - Listar por instructor", instructorId);
        List<ClaseResponse> clases = claseServicio.listarPorInstructor(instructorId);
        return ResponseEntity.ok(RespuestaApi.ok("Total de clases: " + clases.size(), clases));
    }

    @GetMapping("/dia/{diaSemana}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA', 'MIEMBRO')")
    @Operation(summary = "Listar clases por día", description = "Obtiene todas las clases de un día específico de la semana")
    @ApiResponse(responseCode = "200", description = "Lista de clases del día")
    public ResponseEntity<RespuestaApi<List<ClaseResponse>>> listarPorDia(@PathVariable DiaSemana diaSemana) {
        log.info("GET /api/v1/clases/dia/{} - Listar por día", diaSemana);
        List<ClaseResponse> clases = claseServicio.listarPorDia(diaSemana);
        return ResponseEntity.ok(RespuestaApi.ok("Total de clases: " + clases.size(), clases));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA', 'MIEMBRO')")
    @Operation(summary = "Listar todas las clases activas", description = "Obtiene la lista de todas las clases activas")
    @ApiResponse(responseCode = "200", description = "Lista de clases")
    public ResponseEntity<RespuestaApi<List<ClaseResponse>>> listar() {
        log.info("GET /api/v1/clases - Listar clases activas");
        List<ClaseResponse> clases = claseServicio.listar();
        return ResponseEntity.ok(RespuestaApi.ok("Total de clases activas: " + clases.size(), clases));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cancelar clase", description = "Cancela una clase (cambiar estado a CANCELADA)")
    @ApiResponse(responseCode = "200", description = "Clase cancelada exitosamente")
    @ApiResponse(responseCode = "404", description = "Clase no encontrada")
    public ResponseEntity<RespuestaApi<Void>> cancelar(@PathVariable Long id) {
        log.info("DELETE /api/v1/clases/{} - Cancelar clase", id);
        claseServicio.cancelar(id);
        return ResponseEntity.ok(RespuestaApi.ok("Clase cancelada exitosamente", null));
    }
}
