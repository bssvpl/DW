package com.galeria.estilo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.galeria.estilo.service.DatosPerService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsuarioController {
    
    @Autowired
    private DatosPerService datosPerService;

    @GetMapping("/usuarios")
    public String listarUsuarios(HttpServletRequest request, Model model) {
        model.addAttribute("datosPersonales", datosPerService.getAllDatos());

        String currentUrl = request.getRequestURI();
        model.addAttribute("currentUrl", currentUrl);
        
        return "GestionUsuarios/VistaUsuarios";
    }

    @GetMapping("/crearUsuario")
    public String crear(Model model) {
        return "GestionUsuarios/NuevoUsuario";
    }
    
}

