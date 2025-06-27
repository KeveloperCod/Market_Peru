package com.cibertec.servicelmplement;

import com.cibertec.dto.UsuarioDTO;
import com.cibertec.model.Usuario;
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
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean validarCredenciales(String correo, String clave) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        return usuario != null && passwordEncoder.matches(clave, usuario.getClave());
    }



    @Override
    public Usuario registrarUsuarioConEncriptacion(Usuario usuario) {
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarUsuarioPorId(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarUsuarioPorId(int id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public List<UsuarioDTO> listarUsuariosConRol() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ListarUsuariosxRol");
        List<Object[]> result = query.getResultList();
        List<UsuarioDTO> usuarios = new ArrayList<>();
        for (Object[] row : result) {
            usuarios.add(new UsuarioDTO(row));
        }
        return usuarios;
    }
}
