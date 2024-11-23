package com.galeria.estilo.controller;

import com.galeria.estilo.model.Usuario;
import com.galeria.estilo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("user")
public class TroncoController {

    @Autowired
    private UsuarioService userService;

    @PostMapping("/login")
    public String iniciarSesion(@RequestParam("username") String username, 
                                @RequestParam("password") String password, 
                                Model model) {
        System.out.println("Logineacionnnnnn");
        Usuario user = userService.validateUser(username, password);
        if (user != null) {
            System.out.println("Usuario: " + user.getNombre()+"\nid: "+user.getId());
            model.addAttribute("user", user);
            System.out.println("Inicio correcto");
            return "redirect:/home"; // AdminController ;)
        } else {
            System.out.println("Usuario o contraseña incorrectos");
            return "Login/iniciarSesion";
        }
    }

    @ModelAttribute("user")
    public Usuario setUpUserForm() {
        return new Usuario();
    }

    @GetMapping("/login")
    public String login() {
        return "Login/iniciarSesion";
    }

    @GetMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/login";
    }
}