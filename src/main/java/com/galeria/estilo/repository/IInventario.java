package com.galeria.estilo.repository;

import com.galeria.estilo.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInventario extends JpaRepository<Inventario, Integer> {
}
