package com.cibertec.dto;

import java.util.Date;
import lombok.Data;

@Data
public class DetalleVentaDTO {

    private Integer idDetalleVenta;
    private Integer idVenta;
    private Date    fechaVenta;

    private String  descripcionProducto;
    private Integer cantidad;
    private String  precioTexto;
    private String  totalTexto;

    public DetalleVentaDTO(Object[] row) {
        this.idDetalleVenta      = (row[0] != null) ? ((Number) row[0]).intValue() : null;
        this.idVenta             = (row[1] != null) ? ((Number) row[1]).intValue() : null;
        this.fechaVenta          = (row[2] instanceof Date) ? (Date) row[2] : null;

        this.descripcionProducto = (String) row[3];
        this.cantidad            = (row[4] != null) ? ((Number) row[4]).intValue() : null;

        double precioUnit        = (row[5] != null) ? ((Number) row[5]).doubleValue() : 0.0;
        double subtotal          = (row[6] != null) ? ((Number) row[6]).doubleValue() : 0.0;

        this.precioTexto         = String.format("S/ %.2f", precioUnit);
        this.totalTexto          = String.format("S/ %.2f", subtotal);
    }
}
