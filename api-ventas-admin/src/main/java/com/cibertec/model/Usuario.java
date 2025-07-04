// Usuario.java
package com.cibertec.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "Usuario")
@NoArgsConstructor
@AllArgsConstructor
@Builder 
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
}
