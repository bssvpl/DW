package com.galeria.estilo.service;

import com.galeria.estilo.model.Comprobante;
import com.galeria.estilo.repository.IComprobante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComprobanteService {

    @Autowired
    private IComprobante IComprobante;

    public List<Comprobante> getComprobantes() {
        return IComprobante.findAll();
    }

    public Optional<Comprobante> getComprobante(Integer id) {
        return IComprobante.findById(id);
    }

    public void save(Comprobante comprobante) {
        IComprobante.save(comprobante);
    }

    public void deleteById(Integer id) {
        IComprobante.deleteById(id);
    }

}
