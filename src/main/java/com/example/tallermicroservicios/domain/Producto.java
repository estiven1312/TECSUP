package com.example.tallermicroservicios.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "productos")
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Double precio;
    private String imagen;
    private String detalles;
    private String estado;

}
