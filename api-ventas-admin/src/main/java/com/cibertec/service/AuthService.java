package com.cibertec.service;

import com.cibertec.crosscutting.utils.JwtUtil;
import com.cibertec.dto.RegistroUsuarioDTO;
import com.cibertec.model.Rol;
import com.cibertec.model.Usuario;
import com.cibertec.repository.RolRepository;
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

    public AuthService(UserDetailsServiceImplement _userDetailsServiceImplement, RolRepository _rolRepository, PasswordEncoder _passwordEncoder, JwtUtil jwtUtil, AuthenticationManagerBuilder _authenticationManagerBuilder) {
        this._userDetailsServiceImplement = _userDetailsServiceImplement;
        this._rolRepository = _rolRepository;
        this._passwordEncoder = _passwordEncoder;
        this.jwtUtil = jwtUtil;
        this._authenticationManagerBuilder = _authenticationManagerBuilder;
    }

    public String authenticate(String email, String password){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authResult = _authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authResult);

        return jwtUtil.getToken(authResult);
    }


    public void registerUser(RegistroUsuarioDTO newUserDto){
        if(_userDetailsServiceImplement.existsByCorreo(newUserDto.getCorreo())){
            throw new IllegalArgumentException("Ya existe este usuario.");
        }

        Rol role = _rolRepository.findByNombre("User").orElseThrow(() -> new RuntimeException("Rol not found"));
        Usuario _user = new Usuario(newUserDto.getNombreCompleto(),newUserDto.getCorreo(), _passwordEncoder.encode(newUserDto.getClave()), role, true, LocalDateTime.now());

        _userDetailsServiceImplement.save(_user);

    }

}
