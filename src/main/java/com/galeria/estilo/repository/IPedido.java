package com.galeria.estilo.repository;

import com.galeria.estilo.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPedido extends JpaRepository<Pedido, Integer> {

}
