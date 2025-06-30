package com.cibertec.service;

import com.cibertec.crosscutting.utils.JwtUtil;
import com.cibertec.dto.LoginResponseDTO;
import com.cibertec.dto.RegistroUsuarioDTO;
import com.cibertec.dto.UsuarioDTO;
import com.cibertec.model.Rol;
import com.cibertec.model.Usuario;
import com.cibertec.repository.RolRepository;
import com.cibertec.repository.UsuarioRepository;
import com.cibertec.servicelmplement.UserDetailsServiceImplement;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserDetailsServiceImplement _userDetailsServiceImplement;
    private final RolRepository _rolRepository;
    private final PasswordEncoder _passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManagerBuilder _authenticationManagerBuilder;
    private final UsuarioRepository usuarioRepository;

    public AuthService(UserDetailsServiceImplement _userDetailsServiceImplement,
                       RolRepository _rolRepository,
                       PasswordEncoder _passwordEncoder,
                       JwtUtil jwtUtil,
                       AuthenticationManagerBuilder _authenticationManagerBuilder,
                       UsuarioRepository usuarioRepository) {
        this._userDetailsServiceImplement = _userDetailsServiceImplement;
        this._rolRepository = _rolRepository;
        this._passwordEncoder = _passwordEncoder;
        this.jwtUtil = jwtUtil;
        this._authenticationManagerBuilder = _authenticationManagerBuilder;
        this.usuarioRepository = usuarioRepository;
    }

    public LoginResponseDTO authenticate(String email, String password) {

        // 1) Autenticación estándar
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(email, password);

        Authentication authResult =
            _authenticationManagerBuilder.getObject().authenticate(authToken);

        SecurityContextHolder.getContext().setAuthentication(authResult);

        // 2) Generamos el JWT
        String token = jwtUtil.getToken(authResult);

        // 3) Obtenemos los datos del usuario
        Usuario usuario = usuarioRepository.findByCorreo(email);

        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .nombreCompleto(usuario.getNombreCompleto())
                .correo(usuario.getCorreo())
                .rol(usuario.getRol())           // o usuario.getRol().getNombre()
                .build();

        // 4) Devolvemos token + objeto usuario
        return new LoginResponseDTO(token, usuarioDTO);
    }


    public void registerUser(RegistroUsuarioDTO newUserDto){
        if(_userDetailsServiceImplement.existsByCorreo(newUserDto.getCorreo())){
            throw new IllegalArgumentException("Ya existe este usuario.");
        }

        Rol role = _rolRepository.findByNombre("User")
            .orElseThrow(() -> new RuntimeException("Rol not found"));

        Usuario _user = Usuario.builder()
            .nombreCompleto(newUserDto.getNombreCompleto())
            .correo(newUserDto.getCorreo())
            .clave(_passwordEncoder.encode(newUserDto.getClave()))
            .rol(role)
            .esActivo(true)
            .fechaRegistro(LocalDateTime.now())
            .build();

        _userDetailsServiceImplement.save(_user); // o usuarioRepository.save(_user);
    }

}
