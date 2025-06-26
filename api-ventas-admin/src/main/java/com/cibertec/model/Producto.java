package com.cibertec.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProducto")
    private int idProducto;

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "idCategoria", referencedColumnName = "idCategoria")
    private Categoria categoria;

    @Column(name = "stock")
    private int stock;

    @Column(name = "precio")
    private double precio;

    @Column(name = "esActivo")
    private boolean esActivo;

    @Column(name = "fechaRegistro", insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;
}

