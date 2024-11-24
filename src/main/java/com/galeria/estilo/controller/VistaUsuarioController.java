package com.galeria.estilo.controller;

import com.galeria.estilo.model.Producto;
import com.galeria.estilo.model.Usuario;
import com.galeria.estilo.model.DatosPer;
import com.galeria.estilo.service.ProductoService;
import com.galeria.estilo.service.CarritoService;

//import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/vistausuario")
public class VistaUsuarioController {

    private final Logger LOGGER = LoggerFactory.getLogger(VistaUsuarioController.class);

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CarritoService carritoService;

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
    public String mostrarPago(@SessionAttribute(value = "user", required = false) Usuario user, Model model) {
        LOGGER.info("Redirigiendo a la página de pago");
        
        if (user == null) {
            LOGGER.warn("Usuario no encontrado en sesión");
            return "redirect:/login";
        }

        // Añadimos el usuario explícitamente al modelo
        model.addAttribute("user", user);
        
        // Añadimos los datos del carrito
        model.addAttribute("subtotal", carritoService.getSubtotal());
        model.addAttribute("descuentoTotal", carritoService.getDescuentoTotal());
        model.addAttribute("total", carritoService.getTotal());
        model.addAttribute("cantidadTotal", carritoService.getCantidadTotal());

        return "Carrito/pago";
    }
    


    @GetMapping("/bag/{id}")
    public String agregarAlCarrito(@PathVariable Integer id, Model model) {
        Optional<Producto> producto = productoService.getProducto(id);
        if (producto.isPresent()) {
            carritoService.agregarProducto(producto.get(), 1);
            return "redirect:/vistausuario/bag";
        }
        return "redirect:/vistausuario/carrito";
    }

    @GetMapping("/bag")
    public String verCarrito(Model model) {
        model.addAttribute("items", carritoService.getItems());
        model.addAttribute("subtotal", carritoService.getSubtotal());
        model.addAttribute("descuentoTotal", carritoService.getDescuentoTotal());
        model.addAttribute("total", carritoService.getTotal());
        model.addAttribute("cantidadTotal", carritoService.getCantidadTotal());
        return "Carrito/bag";
    }

    @GetMapping("/carrito")
    public String mostrarCarrito(
            @RequestParam(value = "guest", required = false) String guest,
            @SessionAttribute(value = "user", required = false) Usuario user,
            Model model) {
        
        // Añadimos el usuario al modelo explícitamente
        model.addAttribute("user", user);
        model.addAttribute("guest", guest);
        
        // Añadimos los datos del carrito
        model.addAttribute("subtotal", carritoService.getSubtotal());
        model.addAttribute("descuentoTotal", carritoService.getDescuentoTotal());
        model.addAttribute("total", carritoService.getTotal());
        model.addAttribute("cantidadTotal", carritoService.getCantidadTotal());
        
        // Si no hay usuario y no es invitado, muestra el modal
        if (user == null && !"true".equals(guest)) {
            model.addAttribute("showModal", true);
        }
        
        return "Carrito/carrito";
    }
    
    @GetMapping("/api/user-data")
    @ResponseBody
    public Map<String, Object> obtenerUsuarioDatos(@SessionAttribute(value = "user", required = false)
     Usuario user) {
        // Agregué logs detallados para rastrear el flujo de datos
        Map<String, Object> response = new HashMap<>();
          
        try {
            if (user != null) {
                System.out.println("Usuario encontrado ID: " + user.getId() + ", Nombre: " + user.getNombre());
                
                DatosPer datos = user.getDatosPer();
                if (datos != null) {
                    System.out.println("Datos completos del usuario:");
                    System.out.println("DNI: " + datos.getDni());
                    System.out.println("Nombre: " + datos.getNombre());
                    System.out.println("Apellidos: " + datos.getApellidos());
                    System.out.println("Dirección: " + datos.getDireccion());
                    System.out.println("Teléfono: " + datos.getTelefono());
                    System.out.println("Correo: " + datos.getCorreo());
                    
                    response.put("nombre", datos.getNombre());
                    response.put("apellidos", datos.getApellidos());
                    response.put("dni", datos.getDni());
                    response.put("direccion", datos.getDireccion());
                    response.put("telefono", datos.getTelefono());
                    response.put("correo", datos.getCorreo());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("Respuesta final: " + response);
        return response;
    }

    @PostMapping("/bag/actualizar/{id}")
    @ResponseBody
    public Map<String, Object> actualizarCantidad(@PathVariable Integer id, @RequestParam int cantidad) {
        Optional<Producto> producto = productoService.getProducto(id);
        if (producto.isPresent()) {
            carritoService.agregarProducto(producto.get(), cantidad);
        }
        return Map.of(
            "subtotal", carritoService.getSubtotal(),
            "descuentoTotal", carritoService.getDescuentoTotal(),
            "total", carritoService.getTotal(),
            "cantidadTotal", carritoService.getCantidadTotal()
        );
    }

    @GetMapping("/bag/eliminar/{id}")
    public String eliminarDelCarrito(@PathVariable Integer id) {
        carritoService.eliminarProducto(id);
        return "redirect:/vistausuario/bag";
    }

}
