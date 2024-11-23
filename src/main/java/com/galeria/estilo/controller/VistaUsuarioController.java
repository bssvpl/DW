package com.galeria.estilo.controller;

import com.galeria.estilo.model.Producto;
import com.galeria.estilo.model.Usuario;
import com.galeria.estilo.service.ProductoService;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/vistausuario")
public class VistaUsuarioController {

    private final Logger LOGGER = LoggerFactory.getLogger(VistaUsuarioController.class);

    @Autowired
    private ProductoService productoService;

    // Método para ver los productos de una tienda
    

    // Método para ver el detalle de un producto
    @GetMapping("/producto/{id}")
    public String verProducto(@PathVariable Integer id, Model model) {
        Optional<Producto> producto = productoService.getProducto(id);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            return "Carrito/productodetalle";
        } else {
            return "redirect:/productos";
        }
    }   
    


  

    @PostMapping("/pago")
    public String mostrarPago(Model model) {
        LOGGER.info("Redirigiendo a la página de pago");
    
        // No es necesario buscar un producto, ya que no se está pasando un ID
        return "Carrito/pago"; // Redirige a la vista de pago
    }
    


    @GetMapping({"/bag", "/bag/{id}"})
    public String bag(@PathVariable(value = "id", required = false) Integer id, Model model) {
        if (id != null) {
            LOGGER.info("ID enviado como parámetro: {}", id);
            Optional<Producto> producto = productoService.getProducto(id);
            if (producto.isPresent()) {
                model.addAttribute("producto", producto.get());
                return "Carrito/bag";
            } else {
                LOGGER.warn("Producto con ID {} no encontrado", id);
                return "redirect:/productos";
            }
        } else {
            LOGGER.info("Redirigiendo de /bag a /carrito");
            return "redirect:/vistausuario/carrito"; // Redirige al endpoint de carrito
        }
    }
    

    @GetMapping("/carrito")
    public String mostrarCarrito(
            @RequestParam(value = "guest", required = false) String guest,
            @SessionAttribute(value = "user", required = false) Usuario user,
            Model model) {
        // Si no hay usuario y no es invitado, redirige al login
        if (user == null && !"true".equals(guest)) {
            return "redirect:/login"; // Redirige al login si no es usuario registrado ni invitado
        }

        // Si es invitado, muestra un mensaje
        if ("true".equals(guest)) {
            model.addAttribute("mensaje", "Continuando como invitado");
        } else if (user != null) {
            model.addAttribute("mensaje", "Bienvenido de nuevo, " + user.getNombre());
        }
        
        return "Carrito/carrito"; // Si está logueado, muestra la página del carrito
    }
    
    @GetMapping("/api/user-data")
@ResponseBody
public Usuario obtenerUsuarioDatos(HttpServletRequest request) {
    // Obtén el usuario logueado
    Usuario usuario = (Usuario) request.getAttribute("usuario"); // Ajusta según cómo obtengas al usuario logueado
    return usuario;
}

}
