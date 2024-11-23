package com.galeria.estilo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.galeria.estilo.service.PedidoService;
import com.galeria.estilo.service.ProductoService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PedidosController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
	private ProductoService productoService;

    @GetMapping("/pedidos")
    public String listarVentas(HttpServletRequest request, Model model) {
        model.addAttribute("pedidos", pedidoService.getPedidos());

        String currentUrl = request.getRequestURI();
        model.addAttribute("currentUrl", currentUrl);
        
        return "Pedidos/VistaPedido";
    }

    @GetMapping("/crearPedido")
    public String crear(Model model) {
        model.addAttribute("productos", productoService.getProductos());
        return "Pedidos/NuevoPedido";
    }

     
    


}

