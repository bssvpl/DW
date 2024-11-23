package com.galeria.estilo.repository;

import com.galeria.estilo.model.DatosPer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDatosPer extends JpaRepository<DatosPer, Integer> {
}