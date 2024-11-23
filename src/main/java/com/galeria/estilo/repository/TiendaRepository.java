package com.galeria.estilo.repository;

import com.galeria.estilo.model.TiendaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiendaRepository extends JpaRepository<TiendaModel, Integer> {
}