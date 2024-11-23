package com.galeria.estilo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "tiendas")
public class TiendaModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nombre_tienda")
    private String nombreTienda;
    
    @OneToMany(mappedBy = "tienda")
    private List<ProductoModel> productos;
}