package com.galeria.estilo.service;

import com.galeria.estilo.model.Tienda;
import com.galeria.estilo.repository.ITienda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TiendaService {

    @Autowired
    private ITienda ITienda;

    public List<Tienda> getTiendas() {
        return ITienda.findAll();
    }

    public Optional<Tienda> getTienda(Integer id) {
        return ITienda.findById(id);
    }

    public void save(Tienda tienda) {
        ITienda.save(tienda);
    }

    public void delete(Integer id) {
        ITienda.deleteById(id);
    }

}
