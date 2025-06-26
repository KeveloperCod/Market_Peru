package com.cibertec.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "MenuRol")
public class MenuRol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMenuRol")
    private Integer idMenuRol;

    @ManyToOne
    @JoinColumn(name = "idRol", referencedColumnName = "idRol", nullable = false)
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "idMenu", referencedColumnName = "idMenu", nullable = false)
    private Menu menu;
}
