package com.galeria.estilo.controller;

import com.galeria.estilo.model.*;
import com.galeria.estilo.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

@Controller
@RequestMapping("/pago")
public class PagoController {
    
    private static final Logger logger = LoggerFactory.getLogger(PagoController.class);
    
    @Autowired
    private CarritoService carritoService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private DetPedidoService detPedidoService;

    @Autowired
    private ComprobanteService comprobanteService;

    @PostMapping("/procesar")
    @ResponseBody
    public Map<String, Object> procesarPago(
            @RequestBody Map<String, Object> datos,
            @SessionAttribute(value = "user", required = false) Usuario user) {
        
        logger.info("Iniciando procesamiento de pago");
        logger.info("Datos recibidos: {}", datos);
        logger.info("Usuario en sesión: {}", user != null ? user.getId() : "null");
        
        try {
            // Verificar que haya un usuario autenticado
            if (user == null) {
                logger.warn("Intento de pago sin usuario autenticado");
                return Map.of("success", false, "error", "Debe iniciar sesión para realizar la compra");
            }

            // Verificar que el carrito no esté vacío
            if (carritoService.getItems().isEmpty()) {
                return Map.of("success", false, "error", "El carrito está vacío");
            }

            // 1. Crear el Pedido con número de comprobante
            Pedido pedido = new Pedido();
            pedido.setUsuario(user);
            pedido.setEstado("Pendiente");
            pedido.setFechaEntrega(LocalDateTime.now().plusDays(3));
            
            // Generar número de comprobante para el pedido
            String numeroComprobante = String.format("A%03d-%05d", 
                pedidoService.obtenerUltimoId() + 1, 
                comprobanteService.obtenerUltimoNumero() + 1
            );
            pedido.setNumeroComprobante(numeroComprobante);
            pedidoService.save(pedido);

            // 2. Crear los DetPedido y asociarlos al pedido
            Collection<DetPedido> itemsCarrito = carritoService.getItems();
            for (DetPedido itemCarrito : itemsCarrito) {
                DetPedido detPedido = new DetPedido();
                detPedido.setPedido(pedido);
                detPedido.setProducto(itemCarrito.getProducto());
                detPedido.setCantidad(itemCarrito.getCantidad());
                detPedido.setPrecioUni(itemCarrito.getPrecioUni());
                detPedidoService.save(detPedido);
            }

            // 3. Crear el Comprobante con número de tarjeta
            Comprobante comprobante = new Comprobante();
            comprobante.setPedido(pedido);
            comprobante.setSerie(datos.get("numeroTarjeta").toString().substring(datos.get("numeroTarjeta").toString().length() - 4));
            comprobante.setNumero(comprobanteService.obtenerUltimoNumero() + 1);
            comprobante.setMonto(carritoService.getTotal());
            comprobante.setMetodo(datos.get("metodoPago").toString());
            comprobante.setTipoComprobante(datos.get("tipoComprobante").toString());
            comprobante.setRuc("20123456789");
            comprobanteService.save(comprobante);

            // 4. Limpiar el carrito
            carritoService.getItems().clear();

            // 5. Preparar respuesta
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("success", true);
            respuesta.put("numeroComprobante", numeroComprobante);
            respuesta.put("total", comprobante.getMonto());
            respuesta.put("fechaEntrega", pedido.getFechaEntrega().toString());
            respuesta.put("nombreComprador", user.getDatosPer().getNombre() + " " + user.getDatosPer().getApellidos());
            respuesta.put("dniComprador", user.getDatosPer().getDni());
            respuesta.put("direccion", user.getDatosPer().getDireccion());
            respuesta.put("telefono", user.getDatosPer().getTelefono());
            respuesta.put("email", user.getDatosPer().getCorreo());

            return respuesta;

        } catch (Exception e) {
            logger.error("Error al procesar el pago: ", e);
            return Map.of("success", false, "error", e.getMessage());
        }
    }
}