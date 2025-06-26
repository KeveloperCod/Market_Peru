package com.cibertec.servicelmplement;

import java.util.ArrayList;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cibertec.dto.UsuarioDTO;
import com.cibertec.model.Usuario;
import com.cibertec.repository.UsuarioRepository;
import com.cibertec.service.UsuarioService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @PersistenceContext
    private EntityManager entityManager;
    
    
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    
    @Override
    public boolean validarCredenciales(String correo, String clave) {
        Usuario usuario = usuarioRepository.findByCorreoAndClave(correo, clave);
        return usuario != null; 
    }

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
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
    @Transactional // <-- sin readOnly = true
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
	
