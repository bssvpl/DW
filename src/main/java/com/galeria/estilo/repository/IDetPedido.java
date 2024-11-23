package com.galeria.estilo.repository;

import com.galeria.estilo.model.DetPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import java.util.List;

@Repository
public interface IDetPedido extends JpaRepository<DetPedido, Integer> {

}
