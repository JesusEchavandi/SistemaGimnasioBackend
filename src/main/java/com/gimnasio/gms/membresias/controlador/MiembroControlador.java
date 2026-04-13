package com.gimnasio.gms.membresias.controlador;

import com.gimnasio.gms.comun.dto.RespuestaApi;
import com.gimnasio.gms.membresias.dto.CrearMiembroRequest;
import com.gimnasio.gms.membresias.dto.ActualizarMiembroRequest;
import com.gimnasio.gms.membresias.dto.MiembroResponse;
import com.gimnasio.gms.membresias.servicio.MiembroServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/api/v1/miembros")
@RequiredArgsConstructor
@Tag(name = "Miembros", description = "Gestión de miembros del gimnasio")
public class MiembroControlador {

    private final MiembroServicio miembroServicio;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Crear nuevo miembro", description = "Crea un nuevo miembro en el sistema")
    @ApiResponse(responseCode = "201", description = "Miembro creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    public ResponseEntity<RespuestaApi<MiembroResponse>> crear(@RequestBody @Valid CrearMiembroRequest request) {
        log.info("POST /api/v1/miembros - Crear miembro");
        MiembroResponse miembro = miembroServicio.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RespuestaApi.ok("Miembro creado exitosamente", miembro));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Actualizar miembro", description = "Actualiza los datos de un miembro existente")
    @ApiResponse(responseCode = "200", description = "Miembro actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Miembro no encontrado")
    public ResponseEntity<RespuestaApi<MiembroResponse>> actualizar(
            @PathVariable Long id,
            @RequestBody @Valid ActualizarMiembroRequest request) {
        log.info("PUT /api/v1/miembros/{} - Actualizar miembro", id);
        MiembroResponse miembro = miembroServicio.actualizar(id, request);
        return ResponseEntity.ok(RespuestaApi.ok("Miembro actualizado exitosamente", miembro));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA', 'MIEMBRO')")
    @Operation(summary = "Obtener miembro por ID", description = "Obtiene los detalles de un miembro específico")
    @ApiResponse(responseCode = "200", description = "Miembro encontrado")
    @ApiResponse(responseCode = "404", description = "Miembro no encontrado")
    public ResponseEntity<RespuestaApi<MiembroResponse>> obtener(@PathVariable Long id) {
        log.info("GET /api/v1/miembros/{} - Obtener miembro", id);
        MiembroResponse miembro = miembroServicio.obtener(id);
        return ResponseEntity.ok(RespuestaApi.ok("Miembro obtenido exitosamente", miembro));
    }

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA', 'MIEMBRO')")
    @Operation(summary = "Obtener miembro por usuario", description = "Obtiene el miembro asociado a un usuario")
    @ApiResponse(responseCode = "200", description = "Miembro encontrado")
    @ApiResponse(responseCode = "404", description = "Miembro no encontrado")
    public ResponseEntity<RespuestaApi<MiembroResponse>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        log.info("GET /api/v1/miembros/usuario/{} - Obtener por usuario", usuarioId);
        MiembroResponse miembro = miembroServicio.obtenerPorUsuario(usuarioId);
        return ResponseEntity.ok(RespuestaApi.ok("Miembro obtenido exitosamente", miembro));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @Operation(summary = "Listar todos los miembros", description = "Obtiene la lista de todos los miembros")
    @ApiResponse(responseCode = "200", description = "Lista de miembros")
    public ResponseEntity<RespuestaApi<List<MiembroResponse>>> listar() {
        log.info("GET /api/v1/miembros - Listar miembros");
        List<MiembroResponse> miembros = miembroServicio.listar();
        return ResponseEntity.ok(RespuestaApi.ok("Total de miembros: " + miembros.size(), miembros));
    }
}
