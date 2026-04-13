package com.gimnasio.gms.seguridad.repositorio;

import com.gimnasio.gms.seguridad.entidad.TokenRefresco;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenRefrescoRepositorio extends JpaRepository<TokenRefresco, Long> {

    Optional<TokenRefresco> findByTokenAndRevocadoFalse(String token);

    @Modifying
    @Query("update TokenRefresco tr set tr.revocado = true where tr.usuario.id = :usuarioId and tr.revocado = false")
    void revocarPorUsuario(@Param("usuarioId") Long usuarioId);

    @Modifying
    @Query("delete from TokenRefresco tr where tr.expiraEn < :fecha")
    void eliminarExpirados(@Param("fecha") LocalDateTime fecha);
}
