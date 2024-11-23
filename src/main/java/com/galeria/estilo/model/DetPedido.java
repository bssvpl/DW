package com.galeria.estilo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "det_pedidos")
public class DetPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "precio_uni", nullable = false)
    private double precioUni;

    @OneToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

}
