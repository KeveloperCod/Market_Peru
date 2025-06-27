package com.cibertec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistroUsuarioDTO {

    public String nombreCompleto;
    public String correo;
    public String clave;

}
