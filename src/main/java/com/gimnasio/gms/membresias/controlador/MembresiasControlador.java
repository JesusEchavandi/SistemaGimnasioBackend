package com.gimnasio.gms.membresias.controlador;

import com.gimnasio.gms.comun.dto.RespuestaApi;
import com.gimnasio.gms.membresias.dto.CrearMembresiasRequest;
import com.gimnasio.gms.membresias.dto.MembresiasResponse;
import com.gimnasio.gms.membresias.dto.MembresiasAVencerResponse;
import com.gimnasio.gms.membresias.servicio.MembresiasServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/membresias")
@RequiredArgsConstructor
@Tag(name = "Membresías", description = "Gestión de membresías de miembros")
public class MembresiasControlador {

    private final MembresiasServicio membresiasServicio;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Crear nueva membresía", description = "Crea una nueva membresía para un miembro")
    @ApiResponse(responseCode = "201", description = "Membresía creada exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    public ResponseEntity<RespuestaApi<MembresiasResponse>> crear(@RequestBody @Valid CrearMembresiasRequest request) {
        log.info("POST /api/v1/membresias - Crear membresía");
        MembresiasResponse membresia = membresiasServicio.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RespuestaApi.ok("Membresía creada exitosamente", membresia));
    }

    @PutMapping("/{id}/renovar")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Renovar membresía", description = "Renueva una membresía existente extendiendo su fecha de vencimiento")
    @ApiResponse(responseCode = "200", description = "Membresía renovada exitosamente")
    @ApiResponse(responseCode = "404", description = "Membresía no encontrada")
    public ResponseEntity<RespuestaApi<MembresiasResponse>> renovar(@PathVariable Long id) {
        log.info("PUT /api/v1/membresias/{}/renovar - Renovar membresía", id);
        MembresiasResponse membresia = membresiasServicio.renovar(id);
        return ResponseEntity.ok(RespuestaApi.ok("Membresía renovada exitosamente", membresia));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA', 'MIEMBRO')")
    @Operation(summary = "Obtener membresía por ID", description = "Obtiene los detalles de una membresía específica")
    @ApiResponse(responseCode = "200", description = "Membresía encontrada")
    @ApiResponse(responseCode = "404", description = "Membresía no encontrada")
    public ResponseEntity<RespuestaApi<MembresiasResponse>> obtener(@PathVariable Long id) {
        log.info("GET /api/v1/membresias/{} - Obtener membresía", id);
        MembresiasResponse membresia = membresiasServicio.obtener(id);
        return ResponseEntity.ok(RespuestaApi.ok("Membresía obtenida exitosamente", membresia));
    }

    @GetMapping("/miembro/{miembroId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA', 'MIEMBRO')")
    @Operation(summary = "Listar membresías por miembro", description = "Obtiene todas las membresías de un miembro específico")
    @ApiResponse(responseCode = "200", description = "Lista de membresías")
    public ResponseEntity<RespuestaApi<List<MembresiasResponse>>> listarPorMiembro(@PathVariable Long miembroId) {
        log.info("GET /api/v1/membresias/miembro/{} - Listar por miembro", miembroId);
        List<MembresiasResponse> membresias = membresiasServicio.listarPorMiembro(miembroId);
        return ResponseEntity.ok(RespuestaApi.ok("Total de membresías: " + membresias.size(), membresias));
    }

    @GetMapping("/activas")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Listar membresías activas", description = "Obtiene todas las membresías activas en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de membresías activas")
    public ResponseEntity<RespuestaApi<List<MembresiasResponse>>> listarActivas() {
        log.info("GET /api/v1/membresias/activas - Listar activas");
        List<MembresiasResponse> membresias = membresiasServicio.listarActivas();
        return ResponseEntity.ok(RespuestaApi.ok("Total de membresías activas: " + membresias.size(), membresias));
    }

    @GetMapping("/proximas-a-vencer")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Obtener membresías próximas a vencer", description = "Obtiene membresías que vencerán en los próximos N días (default: 7)")
    @ApiResponse(responseCode = "200", description = "Lista de membresías próximas a vencer")
    public ResponseEntity<RespuestaApi<List<MembresiasAVencerResponse>>> obtenerProximasAVencer(
            @RequestParam(defaultValue = "7") int diasRestantes) {
        log.info("GET /api/v1/membresias/proximas-a-vencer?diasRestantes={} - Obtener próximas a vencer", diasRestantes);
        List<MembresiasAVencerResponse> membresias = membresiasServicio.obtenerProximasAVencer(diasRestantes);
        return ResponseEntity.ok(RespuestaApi.ok("Total de membresías próximas a vencer: " + membresias.size(), membresias));
    }
}
