package com.galeria.estilo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "comprobantes", uniqueConstraints={@UniqueConstraint(columnNames = {"serie", "numero_comprobante"})})
public class Comprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "serie", length = 10, nullable = false)
    private String serie;

    @Column(name = "numero_comprobante", nullable = false)
    private int numero;
    
    @Column(name = "tipo_comprobante", length = 50)
    private String tipoComprobante;

    @Column(name = "ruc", length = 50)
    private String ruc;

    @Column(name = "monto_pagado", nullable = false)
    private Double monto;

    @Column(name = "fec_emision", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime fechaEmision;

    @Column(name = "metodo_pago", nullable = false, length = 50)
    private String metodo;

    @OneToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
}
