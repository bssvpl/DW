package com.galeria.estilo.service;
import com.galeria.estilo.model.DetPedido;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.galeria.estilo.model.Producto;

@Service
@SessionScope
public class CarritoService {
    private Map<Integer, DetPedido> items = new HashMap<>();

    public void agregarProducto(Producto producto, int cantidad) {
        DetPedido item = items.getOrDefault(producto.getId(), new DetPedido());
        item.setProducto(producto);
        item.setCantidad(cantidad);
        item.setPrecioUni(producto.getPrecio());
        items.put(producto.getId(), item);
    }

    public Collection<DetPedido> getItems() {
        return items.values();
    }

    public void eliminarProducto(Integer productoId) {
        items.remove(productoId);
    }

    public double getSubtotal() {
        return items.values().stream()
                .mapToDouble(item -> item.getProducto().getPrecio() * item.getCantidad())
                .sum();
    }

    public double getDescuentoTotal() {
        return items.values().stream()
                .mapToDouble(item -> {
                    double precioUnitario = item.getProducto().getPrecio();
                    int cantidad = item.getCantidad();
                    double descuentoPorcentaje = item.getProducto().getDescuento() / 100.0;
                    return precioUnitario * cantidad * descuentoPorcentaje;
                })
                .sum();
    }

    public double getTotal() {
        return getSubtotal() - getDescuentoTotal();
    }

    public int getCantidadTotal() {
        return items.values().stream()
                .mapToInt(DetPedido::getCantidad)
                .sum();
    }
}