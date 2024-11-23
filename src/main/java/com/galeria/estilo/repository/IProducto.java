package com.galeria.estilo.repository;

import com.galeria.estilo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import java.util.List;

@Repository
public interface IProducto extends JpaRepository<Producto, Integer> {

}
