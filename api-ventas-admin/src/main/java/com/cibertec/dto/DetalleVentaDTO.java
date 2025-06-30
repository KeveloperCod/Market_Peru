package com.cibertec.dto;

import java.util.Date;

import lombok.Data;
@Data
public class DetalleVentaDTO {
	
    public Integer idDetalleVenta;
    public Integer idVenta;
    public Date fechaVenta;
    public String nombreProducto;
    public Integer cantidad;
    public double precioUnitario;
    public double subtotal;

    public DetalleVentaDTO(Object[] row) {
        this.idDetalleVenta = (row[0] != null) ? ((Number) row[0]).intValue() : null;
        this.idVenta = (row[1] != null) ? ((Number) row[1]).intValue() : null;
        if (row[2] instanceof Date) {
            this.fechaVenta = (Date) row[2];
        } else {
            this.fechaVenta = null;
        }
        this.nombreProducto = (String) row[3];
        this.cantidad = (row[4] != null) ? ((Number) row[4]).intValue() : null;
        this.precioUnitario = (row[5] != null) ? ((Number) row[5]).doubleValue() : 0.0;
        this.subtotal = (row[6] != null) ? ((Number) row[6]).doubleValue() : 0.0;
    }

}
