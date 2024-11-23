package com.galeria.estilo.service;

import com.galeria.estilo.model.DetPedido;
import com.galeria.estilo.repository.IDetPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetPedidoService {

    @Autowired
    private IDetPedido IDetPedido;

    public List<DetPedido> getPedidos() {
        return IDetPedido.findAll();
    }

    public Optional<DetPedido> getPedidoById(Integer id) {
        return IDetPedido.findById(id);
    }

    public void save(DetPedido detPedido) {
        IDetPedido.save(detPedido);
    }

    public void deleteById(Integer id) {
        IDetPedido.deleteById(id);
    }

}
