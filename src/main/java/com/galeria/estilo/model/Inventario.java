package com.galeria.estilo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "inventarios")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Column(name = "notas")
    private String notas;

    @OneToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

}
