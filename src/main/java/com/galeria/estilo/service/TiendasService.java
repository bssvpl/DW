package com.galeria.estilo.service;

import com.galeria.estilo.model.TiendaModel;
import com.galeria.estilo.repository.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TiendasService {
    
    @Autowired
    private TiendaRepository tiendaRepository;
    
    public TiendaModel findById(Integer id) {
        return tiendaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tienda no encontrada con ID: " + id));
    }
}