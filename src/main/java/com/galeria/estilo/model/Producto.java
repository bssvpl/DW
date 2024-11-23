package com.galeria.estilo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_prod", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "talla", nullable = false , length = 10)
    private String talla;

    @Column(name = "marca", nullable = false, length = 50)
    private String marca;

    @Column(name = "color", nullable = false, length = 30)
    private String color;

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "descuento")
    private int descuento;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_tienda")
    private Tienda tienda;


}
