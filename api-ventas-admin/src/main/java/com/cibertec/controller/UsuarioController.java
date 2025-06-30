package com.cibertec.controller;

import com.cibertec.dto.UsuarioDTO;
import com.cibertec.model.Usuario;
import com.cibertec.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/listar")
    public List<Usuario> listar() {
        return usuarioService.listarUsuarios();
    }

    @PostMapping("/registrar")
    public Usuario registrar(@RequestBody Usuario usuario) {
        return usuarioService.registrarUsuarioConEncriptacion(usuario);
    }

    /**  actualizar sin perder la contraseña  */
    @PutMapping("/actualizar")
    public Usuario actualizar(@RequestBody Usuario usuario) {
        return usuarioService.actualizarUsuario(usuario);
    }

    /**  eliminación lógica opcional  */
    @PutMapping("/desactivar/{id}")
    public void desactivar(@PathVariable int id) {
        Usuario u = usuarioService.buscarUsuarioPorId(id);
        if (u == null) throw new RuntimeException("No existe usuario");
        u.setEsActivo(false);
        usuarioService.actualizarUsuario(u);
    }
}
