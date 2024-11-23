package com.galeria.estilo.service;

import com.galeria.estilo.model.Resena;
import com.galeria.estilo.repository.IResena;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResenaService {

    @Autowired
    private IResena IResena;

    public List<Resena> getResenas(){
        return IResena.findAll();
    }

    public Optional<Resena> getResena(Integer id){
        return IResena.findById(id);
    }

    public void save(Resena resena){
        IResena.save(resena);
    }

    public void deleteById(Integer id){
        IResena.deleteById(id);
    }

}
