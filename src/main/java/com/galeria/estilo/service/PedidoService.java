package com.galeria.estilo.service;

import com.galeria.estilo.model.Pedido;
import com.galeria.estilo.repository.IPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private IPedido IPedido;

    public List<Pedido> getPedidos() {
        return IPedido.findAll();
    }

    public Optional<Pedido> getPedido(Integer id) {
        return IPedido.findById(id);
    }

    public void save(Pedido pedido) {
        IPedido.save(pedido);
    }

    public void deleteById(Integer id) {
        IPedido.deleteById(id);
    }

}
