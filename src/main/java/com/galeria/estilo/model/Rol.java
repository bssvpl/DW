package com.galeria.estilo.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rol_nombre", nullable = false, unique = true, length = 50)
    private String nombre;

}
