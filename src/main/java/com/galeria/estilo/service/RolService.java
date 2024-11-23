package com.galeria.estilo.service;

import com.galeria.estilo.model.Rol;
import com.galeria.estilo.repository.IRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private IRol IRol;

    public List<Rol> getRoles() {
        return IRol.findAll();
    }

    public Optional<Rol> getRol(Integer id) {
        return IRol.findById(id);
    }

    public void save(Rol rol) {
        IRol.save(rol);
    }

    public void delete(Integer id) {
        IRol.deleteById(id);
    }

}
