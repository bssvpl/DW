package com.galeria.estilo.repository;

import com.galeria.estilo.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoria extends JpaRepository<Categoria, Integer> {

}
