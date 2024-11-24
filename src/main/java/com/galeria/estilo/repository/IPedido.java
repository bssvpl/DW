package com.galeria.estilo.repository;

import com.galeria.estilo.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IPedido extends JpaRepository<Pedido, Integer> {
    Optional<Pedido> findTopByOrderByIdDesc();
}
