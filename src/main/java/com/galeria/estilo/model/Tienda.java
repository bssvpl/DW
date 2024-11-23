package com.galeria.estilo.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tiendas")
public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_tienda", nullable = false, length = 50, unique = true)
    private String nombre;
}
