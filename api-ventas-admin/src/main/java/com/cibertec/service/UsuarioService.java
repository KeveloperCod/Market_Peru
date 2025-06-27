package com.cibertec.service;

import com.cibertec.dto.UsuarioDTO;
import com.cibertec.model.Usuario;

import java.util.List;

public interface UsuarioService {
    boolean validarCredenciales(String correo, String clave);
    List<Usuario> listarUsuarios();
    List<UsuarioDTO> listarUsuariosConRol();
    Usuario buscarUsuarioPorId(int id);
    Usuario actualizarUsuario(Usuario usuario);
    Usuario registrarUsuarioConEncriptacion(Usuario usuario);
    void eliminarUsuarioPorId(int id);
}
