package com.galeria.estilo.service;

import com.galeria.estilo.model.ProductoModel;
import com.galeria.estilo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductosService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    public List<ProductoModel> findAll() {
        return productoRepository.findAll();
    }
    
    public List<ProductoModel> findByTiendaId(Integer tiendaId) {
        return productoRepository.findByTiendaId(tiendaId);
    }
    
 






}