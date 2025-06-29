package com.cibertec.dto;

import java.util.Date;

import com.cibertec.model.Rol;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class UsuarioDTO {

	
	private Integer idUsuario;
	    private String nombreCompleto;
	    private String correo;
	    private Rol rol;
		public UsuarioDTO(Integer idUsuario, String nombreCompleto, String correo, Rol rol) {
			super();
			this.idUsuario = idUsuario;
			this.nombreCompleto = nombreCompleto;
			this.correo = correo;
			this.rol = rol;
		}
		public UsuarioDTO(Object[] row) {
			// TODO Auto-generated constructor stub
		} 


	
}
