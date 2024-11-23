package com.galeria.estilo.repository;

import com.galeria.estilo.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IResena extends JpaRepository<Resena, Integer> {

}
