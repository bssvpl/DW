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
    private IComprobante comprobanteRepository;

    public List<Comprobante> getComprobantes() {
        return comprobanteRepository.findAll();
    }

    public Optional<Comprobante> getComprobante(Integer id) {
        return comprobanteRepository.findById(id);
    }

    public void save(Comprobante comprobante) {
        comprobanteRepository.save(comprobante);
    }

    public void deleteById(Integer id) {
        comprobanteRepository.deleteById(id);
    }

    public int obtenerUltimoNumero() {
        return comprobanteRepository.findTopByOrderByNumeroDesc()
                .map(Comprobante::getNumero)
                .orElse(10000); // Empezamos desde 10001
    }

    // Nuevo método para generar serie
    public String generarSerie() {
        int ultimoNumero = obtenerUltimoNumero() - 100000;
        return String.format("A%03d", ultimoNumero + 1);
    }
}
