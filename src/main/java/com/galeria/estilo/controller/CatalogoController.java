package com.galeria.estilo.controller;

import com.galeria.estilo.model.ProductoModel;
import com.galeria.estilo.model.TiendaModel;
import com.galeria.estilo.service.ProductosService;
import com.galeria.estilo.service.TiendasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class CatalogoController {
    
    @Autowired
    private ProductosService productosService;
    
    @Autowired
    private TiendasService tiendasService;
    
    @GetMapping("/catalogoproductos")
    public String mostrarCatalogo(@RequestParam(required = false) Integer tienda, Model model) {
        try {
            if (tienda != null) {
                List<ProductoModel> productos = productosService.findByTiendaId(tienda);
                TiendaModel tiendaInfo = tiendasService.findById(tienda);
                
                productos.forEach(this::procesarRutaImagen);
                
                model.addAttribute("productos", productos);
                model.addAttribute("tiendaInfo", tiendaInfo);
                model.addAttribute("tiendaNombre", tiendaInfo.getNombreTienda());
            } else {
                List<ProductoModel> productos = productosService.findAll();
                productos.forEach(this::procesarRutaImagen);
                
                model.addAttribute("productos", productos);
                model.addAttribute("tiendaNombre", "Todas las Tiendas");
            }
            return "Carrito/productos";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar el catálogo: " + e.getMessage());
            return "error";
        }
    }
    
    private void procesarRutaImagen(ProductoModel producto) {
        String rutaImagen = producto.getImagen();
        if (rutaImagen != null && !rutaImagen.isEmpty()) {
            if (!rutaImagen.startsWith("/imagesWeb/img/")) {
                if (rutaImagen.startsWith("/")) {
                    rutaImagen = rutaImagen.substring(1);
                }
                if (rutaImagen.startsWith("img/")) {
                    rutaImagen = rutaImagen.substring(4);
                }
                producto.setImagen("/imagesWeb/img/" + rutaImagen);
            }
        } else {
            producto.setImagen("/imagesWeb/img/default.png");
        }
    }
}