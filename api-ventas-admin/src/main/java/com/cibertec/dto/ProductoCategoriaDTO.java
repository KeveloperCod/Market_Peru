package com.cibertec.dto;

import java.time.LocalDateTime;

public interface ProductoCategoriaDTO {
    int getIdProducto();
    String getNombreProducto();
    String getNombreCategoria();
    int getStock();
    double getPrecio();
    boolean getEsActivo();
    LocalDateTime getFechaRegistro();
}
