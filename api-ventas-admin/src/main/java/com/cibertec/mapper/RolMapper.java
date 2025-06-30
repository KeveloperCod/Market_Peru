package com.cibertec.mapper;

import com.cibertec.dto.RolDTO;
import com.cibertec.model.Rol;

public class RolMapper {

    public static RolDTO toDTO(Rol rol) {
        if (rol == null) return null;
        return RolDTO.builder()
                .idRol(rol.getIdRol())
                .nombre(rol.getNombre())
                .build();
    }

    public static Rol toEntity(RolDTO dto) {
        if (dto == null) return null;
        return Rol.builder()
                .idRol(dto.getIdRol())
                .nombre(dto.getNombre())
                .build();
    }
}
