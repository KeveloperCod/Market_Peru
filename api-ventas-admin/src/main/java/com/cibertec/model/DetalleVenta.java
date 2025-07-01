package com.cibertec.model;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonBackReference;   // <<-- nuevo
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "DetalleVenta")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDetalleVenta")
    private int idDetalleVenta;

    @JsonBackReference 
    @ManyToOne
    @JoinColumn(name = "idVenta", nullable = false)
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "precio", precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;

    @Column(name = "total", precision = 10, scale = 2, nullable = false)
    private BigDecimal total;
}
