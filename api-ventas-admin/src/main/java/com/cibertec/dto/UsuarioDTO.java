package com.cibertec.dto;

import java.util.Date;

public class UsuarioDTO {

    public Integer idUsuario;
    public String nombreCompleto;
    public String correo;
    public String rol;
    public Boolean esActivo;
    public Date fechaRegistro;

    public UsuarioDTO(Object[] row) {
        this.idUsuario = (row[0] != null) ? ((Number) row[0]).intValue() : null;
        this.nombreCompleto = (String) row[1];
        this.correo = (String) row[2];
        this.rol = (String) row[3];
        if (row[4] instanceof Boolean) {
            this.esActivo = (Boolean) row[4];
        } else if (row[4] instanceof Number) {
            this.esActivo = ((Number) row[4]).intValue() == 1;
        } else {
            this.esActivo = false;
        }
        this.fechaRegistro = (Date) row[5];
    }
	
}
