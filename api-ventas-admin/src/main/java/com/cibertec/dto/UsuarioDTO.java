package com.cibertec.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Integer idUsuario;
    private String nombreCompleto;
    private String correo;
    private RolDTO rol; 
    private Boolean esActivo;
    private String clave;

    public UsuarioDTO(Object[] row) {

    }
}
