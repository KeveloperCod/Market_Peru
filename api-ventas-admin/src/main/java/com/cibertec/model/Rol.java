// Rol.java
package com.cibertec.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "Rol")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRol;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;
}
