package com.galeria.estilo.service;

import com.galeria.estilo.model.DatosPer;
import com.galeria.estilo.repository.IDatosPer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DatosPerService {

    @Autowired
    private IDatosPer IDatosPer;

    public List<DatosPer> getAllDatos(){
        return IDatosPer.findAll();
    }

    public Optional<DatosPer> getDatosPer(Integer id){
        return IDatosPer.findById(id);
    }

    public void save(DatosPer datosPer){
        IDatosPer.save(datosPer);
    }

    public void deleteById(Integer id){
        IDatosPer.deleteById(id);
    }

}
