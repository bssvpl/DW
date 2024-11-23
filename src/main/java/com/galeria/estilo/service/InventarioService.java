package com.galeria.estilo.service;

import com.galeria.estilo.model.Inventario;
import com.galeria.estilo.repository.IInventario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    @Autowired
    private IInventario IInventario;

    public List<Inventario> getInventarios(){
        return IInventario.findAll();
    }

    public Optional<Inventario> getInventario(Integer id){
        return IInventario.findById(id);
    }

    public void save(Inventario inventario){
        IInventario.save(inventario);
    }

    public void deleteById(Integer id){
        IInventario.deleteById(id);
    }

}
