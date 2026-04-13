package com.gimnasio.gms.seguridad.servicio;

import com.gimnasio.gms.seguridad.entidad.UsuarioSistema;
import com.gimnasio.gms.seguridad.repositorio.UsuarioSistemaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DetalleUsuarioServicio implements UserDetailsService {

    private final UsuarioSistemaRepositorio usuarioSistemaRepositorio;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioSistema usuario = usuarioSistemaRepositorio.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User.withUsername(usuario.getNombreUsuario())
                .password(usuario.getClave())
                .authorities(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()))
                .disabled(!usuario.isActivo())
                .build();
    }
}
