package com.cibertec.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Integer idUsuario;

    @Column(length = 100)
    private String nombreCompleto;

    @Column(length = 40, unique = true)
    private String correo;

    @ManyToOne
    @JoinColumn(name = "idRol", nullable = false)
    private Rol rol;

    @Column(length = 40)
    private String clave;

    private Boolean esActivo;

    @Column(insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    public Usuario(String nombreCompleto, String correo, String clave, Rol rol, Boolean esActivo, LocalDateTime fechaRegistro) {
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.clave = clave;
        this.rol = rol;
        this.esActivo = esActivo;
        this.fechaRegistro = fechaRegistro;
    }
}
