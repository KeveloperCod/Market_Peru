package com.cibertec.service;

import com.cibertec.dto.UsuarioDTO;
import com.cibertec.model.Usuario;

import java.util.List;

public interface UsuarioService {
    boolean validarCredenciales(String correo, String clave);
    Usuario registrarUsuario(Usuario usuario);
    List<Usuario> listarUsuarios();
    Usuario buscarUsuarioPorId(int id);
    void eliminarUsuarioPorId(int id);
    Usuario actualizarUsuario(Usuario usuario);
    
    
    // Listar Usuarios x Rol
    List<UsuarioDTO> listarUsuariosConRol();
    
}
