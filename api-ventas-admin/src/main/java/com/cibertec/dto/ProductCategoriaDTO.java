package com.cibertec.dto;

import java.util.Date;

public class ProductCategoriaDTO {

    public Integer idProducto;
    public String nombreProducto;
    public String nombreCategoria;
    public Integer stock;
    public double precio;          
    public Boolean esActivo;
    public Date fechaRegistro;

    public ProductCategoriaDTO(Object[] row) {
        this.idProducto = (row[0] != null) ? ((Number) row[0]).intValue() : null;
        this.nombreProducto = (String) row[1];
        this.nombreCategoria = (String) row[2];
        this.stock = (row[3] != null) ? ((Number) row[3]).intValue() : null;

        // Admite BigDecimal o Number
        if (row[4] instanceof Number) {
            this.precio = ((Number) row[4]).doubleValue();
        } else {
            this.precio = 0.0;
        }

        if (row[5] instanceof Boolean) {
            this.esActivo = (Boolean) row[5];
        } else if (row[5] instanceof Number) {
            this.esActivo = ((Number) row[5]).intValue() == 1;
        } else {
            this.esActivo = false;
        }
        if (row[6] instanceof Date) {
            this.fechaRegistro = (Date) row[6];
        } else {
            this.fechaRegistro = null;
        }
    }
    
}
