package com.cibertec.servicelmplement;

import com.cibertec.model.Rol;
import com.cibertec.repository.RolRepository;
import com.cibertec.service.RolService;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public Rol registrarRol(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    @Override
    public Rol buscarRolPorId(int id) {
        return rolRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarRolPorId(int id) {
        rolRepository.deleteById(id);
    }

    @Override
    public Rol actualizarRol(Rol rol) {
        return rolRepository.save(rol);
    }
}
