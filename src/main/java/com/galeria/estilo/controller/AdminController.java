package com.galeria.estilo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;
import com.galeria.estilo.model.Usuario;
//import com.galeria.estilo.service.UsuarioService;


// @RequestMapping("/administrador")
@Controller
public class AdminController {

    @GetMapping({"/", "/home"})
public String home(HttpServletRequest request, Model model) {
    Usuario user = (Usuario) request.getSession().getAttribute("user");
    if (user == null) {
        System.out.println("Usuario no encontrado");
        return "redirect:/login"; // Redirige al login si no está logueado
    } else {
        System.out.println("Usuario logeado " + user.getNombre());
        model.addAttribute("user", user);

        // Redirige al catálogo de productos si el usuario está logueado
        return "redirect:/catalogoproductos"; // Redirige al método del catálogo de productos
    }
}

}
