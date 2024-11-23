package com.galeria.estilo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "productos")
public class ProductoModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nombre_prod")
    private String nombreProd;
    
    private String descripcion;
    private Double precio;
    private String talla;
    private String marca;
    private String color;
    private Integer cantidad;
    private Integer descuento;
    private String imagen;
    
    @ManyToOne
    @JoinColumn(name = "id_tienda")
    private TiendaModel tienda;
    
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private CategoriaModel categoria;
}