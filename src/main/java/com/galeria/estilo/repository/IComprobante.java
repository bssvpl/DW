package com.galeria.estilo.repository;

import com.galeria.estilo.model.Comprobante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IComprobante extends JpaRepository<Comprobante, Integer> {

}
