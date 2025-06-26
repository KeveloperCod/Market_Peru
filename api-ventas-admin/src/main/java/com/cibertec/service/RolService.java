package com.cibertec.service;

import java.util.List;

import com.cibertec.model.Rol;

public interface RolService {
    Rol registrarRol(Rol rol);
    List<Rol> listarRoles();
    Rol buscarRolPorId(int id);
    void eliminarRolPorId(int id);
    Rol actualizarRol(Rol rol);
}

