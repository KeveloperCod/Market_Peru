package com.cibertec.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "NumeroDocumento")
public class NumeroDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idNumeroDocumento")
    private int idNumeroDocumento;

    @Column(name = "ultimo_Numero", nullable = false)
    private int ultimoNumero;

    @Column(name = "fechaRegistro")
    private LocalDateTime fechaRegistro;
}
