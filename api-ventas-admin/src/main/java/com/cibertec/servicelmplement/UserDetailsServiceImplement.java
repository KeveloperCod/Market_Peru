package com.cibertec.servicelmplement;

import com.cibertec.model.Usuario;
import com.cibertec.repository.UsuarioRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@NoArgsConstructor
@Service
public class UserDetailsServiceImplement implements UserDetailsService {

    @Autowired
    UsuarioRepository _usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = _usuarioRepository.findByCorreo(correo);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con correo: " + correo);
        }

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                "ROLE_" + usuario.getRol().getNombre().toUpperCase());

        return new org.springframework.security.core.userdetails.User(
                usuario.getCorreo(),
                usuario.getClave(),
                Collections.singleton(authority)
        );
    }

    public Boolean existsByCorreo(String correo) {
        return _usuarioRepository.existsByCorreo(correo);
    }

    public void save(Usuario usuario) {
        _usuarioRepository.save(usuario);
    }
    
    
}
