package com.cibertec.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCategoria")
    private int idCategoria;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "esActivo")
    private boolean esActivo;

    @Column(name = "fechaRegistro", updatable = false, insertable = false)
    private LocalDateTime fechaRegistro;
}
