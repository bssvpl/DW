package com.galeria.estilo.repository;

import com.galeria.estilo.model.ProductoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoModel, Integer> {
    List<ProductoModel> findByTiendaId(Integer tiendaId);
}