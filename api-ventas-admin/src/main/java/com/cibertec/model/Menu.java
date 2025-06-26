package com.cibertec.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMenu;

    @Column(length = 50)
    private String nombre;

    @Column(length = 50)
    private String icono;

    @Column(length = 50)
    private String url;
}
