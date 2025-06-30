package com.cibertec.mapper;

import com.cibertec.dto.UsuarioDTO;
import com.cibertec.dto.RolDTO;
import com.cibertec.model.Usuario;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;

        return UsuarioDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .nombreCompleto(usuario.getNombreCompleto())
                .correo(usuario.getCorreo())
                .rol(RolMapper.toDTO(usuario.getRol()))
                .esActivo(usuario.getEsActivo())
                // nota: clave no se incluye por seguridad
                .build();
    }

    // Si algún día necesitas convertir DTO → Entidad, puedes agregar:
    /*
    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) return null;

        return Usuario.builder()
                .idUsuario(dto.getIdUsuario())
                .nombreCompleto(dto.getNombreCompleto())
                .correo(dto.getCorreo())
                .rol(RolMapper.toEntity(dto.getRol()))
                .esActivo(dto.getEsActivo())
                // clave no se incluye aquí, normalmente se establece aparte
                .build();
    }
    */
}
