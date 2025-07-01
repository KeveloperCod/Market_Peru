package com.cibertec.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;   // <<-- nuevo
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

    @JsonManagedReference 
    @OneToMany(mappedBy = "venta",
               cascade = CascadeType.ALL,
               orphanRemoval = true,
               fetch = FetchType.EAGER)
    private List<DetalleVenta> detalleVenta;

    // helper para agregar detalles
    public void addDetalleVenta(DetalleVenta det) { this.detalleVenta.add(det); }
}
