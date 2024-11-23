package com.galeria.estilo.repository;

import com.galeria.estilo.model.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITienda extends JpaRepository<Tienda, Integer> {
}
