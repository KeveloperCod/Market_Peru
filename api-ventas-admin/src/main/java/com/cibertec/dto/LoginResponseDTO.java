// LoginResponseDTO.java
package com.cibertec.dto;

import com.cibertec.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
	 private String token;
	 private UsuarioDTO usuario; 
}
