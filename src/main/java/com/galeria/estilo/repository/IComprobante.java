package com.galeria.estilo.repository;

import com.galeria.estilo.model.Comprobante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IComprobante extends JpaRepository<Comprobante, Integer> {
    Optional<Comprobante> findTopByOrderByNumeroDesc();
}
