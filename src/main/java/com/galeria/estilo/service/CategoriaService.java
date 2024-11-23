package com.galeria.estilo.service;

import com.galeria.estilo.model.Categoria;
import com.galeria.estilo.repository.ICategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private ICategoria ICategoria;

    public List<Categoria> getCategorias() {
        return ICategoria.findAll();
    }

    public Optional<Categoria> getCategoria(Integer id) {
        return ICategoria.findById(id);
    }

    public void save(Categoria categoria) {
        ICategoria.save(categoria);
    }

    public void delete(Integer id) {
        ICategoria.deleteById(id);
    }
}
