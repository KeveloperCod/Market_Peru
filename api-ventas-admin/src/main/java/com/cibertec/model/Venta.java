package com.cibertec.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVenta")
    private int idVenta;

    @Column(name = "numeroDocumento", length = 40, nullable = false)
    private String numeroDocumento;

    @Column(name = "tipoPago", length = 50, nullable = false)
    private String tipoPago;

    @Column(name = "total", precision = 10, scale = 2, nullable = false)
    private BigDecimal total;

    @Column(name = "fechaRegistro")
    private LocalDateTime fechaRegistro;
}
