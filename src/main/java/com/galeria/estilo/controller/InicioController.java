package com.galeria.estilo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    @GetMapping("/Inicio")
    public String mostrarInicio() {
        return "Inicio"; // Esto hace referencia a Inicio.html en templates
    }
    
    // Aquí puedes agregar más métodos para manejar otras funcionalidades
    // relacionadas con la página de inicio, como:
    // - Cargar productos destacados
    // - Manejar el carrito de compras
    // - Gestionar el historial de compras
}