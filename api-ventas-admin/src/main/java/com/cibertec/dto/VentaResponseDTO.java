package com.cibertec.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class VentaResponseDTO {
    private Integer idVenta;
    private String  numeroDocumento;
    private String  tipoPago;
    private BigDecimal total;
    private LocalDateTime fechaRegistro;
    private List<DetalleVentaDTO> detalleVenta; 
}
