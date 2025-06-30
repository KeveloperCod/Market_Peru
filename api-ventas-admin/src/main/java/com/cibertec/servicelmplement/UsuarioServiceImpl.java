package com.cibertec.servicelmplement;

import com.cibertec.dto.UsuarioDTO;
import com.cibertec.model.Rol;
import com.cibertec.model.Usuario;
import com.cibertec.repository.RolRepository;
import com.cibertec.repository.UsuarioRepository;
import com.cibertec.service.UsuarioService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              RolRepository rolRepository,
                              PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository     = rolRepository;
        this.passwordEncoder   = passwordEncoder;
    }

    /* ---------- Autenticación ---------- */

    @Override
    public boolean validarCredenciales(String correo, String clave) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        return usuario != null && passwordEncoder.matches(clave, usuario.getClave());
    }

    /* ---------- CRUD BÁSICO ---------- */

    @Override
    public Usuario registrarUsuarioConEncriptacion(Usuario usuario) {
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        return usuarioRepository.save(usuario);
    }

    @Override public List<Usuario> listarUsuarios()           { return usuarioRepository.findAll(); }
    @Override public Usuario buscarUsuarioPorId(int id)       { return usuarioRepository.findById(id).orElse(null); }
    @Override public void    eliminarUsuarioPorId(int id)     { usuarioRepository.deleteById(id); }

    /* ---------- Actualizar sin perder contraseña ---------- */

    @Override
    public Usuario actualizarUsuario(Usuario usuarioIn) {

        Usuario existente = usuarioRepository.findById(usuarioIn.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 1) campos simples
        existente.setNombreCompleto(usuarioIn.getNombreCompleto());
        existente.setCorreo(usuarioIn.getCorreo());

        // 2) rol: solo si llega idRol válido
        if (usuarioIn.getRol() != null && usuarioIn.getRol().getIdRol() != null) {
            Rol rol = rolRepository.findById(usuarioIn.getRol().getIdRol())
                                   .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            existente.setRol(rol);
        }

        // 3) contraseña: solo si llega algo nuevo
        if (usuarioIn.getClave() != null && !usuarioIn.getClave().isBlank()) {
            existente.setClave(passwordEncoder.encode(usuarioIn.getClave()));
        }

        // 4) estado lógico: si viene null lo conserva
        if (usuarioIn.getEsActivo() != null) {
            existente.setEsActivo(usuarioIn.getEsActivo());
        }

        return usuarioRepository.save(existente);
    }

    /* ---------- Stored procedure opcional ---------- */

    @Override @Transactional
    public List<UsuarioDTO> listarUsuariosConRol() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ListarUsuariosxRol");
        List<Object[]> rs = query.getResultList();
        List<UsuarioDTO> lista = new ArrayList<>();
        rs.forEach(row -> lista.add(new UsuarioDTO(row)));
        return lista;
    }
}
