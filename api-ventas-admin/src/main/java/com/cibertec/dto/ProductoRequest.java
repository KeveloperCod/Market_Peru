package com.cibertec.dto;

import lombok.Data;

@Data
public class ProductoRequest {

	   public String nombre;
	    public Integer idCategoria;
	    public Integer stock;
	    public Double precio;
	
	    private Boolean esActivo;
	
	
}
