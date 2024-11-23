package com.galeria.estilo.controller;

import com.galeria.estilo.model.Rol;
import com.galeria.estilo.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping("/rol")
    public List<Rol> getAll(){
        return rolService.getRoles();
    }

    @GetMapping("/rol/{rolId}")
    public Optional<Rol> getRoleById(@PathVariable("rolId") Integer rolId){
        return rolService.getRol(rolId);
    }

    @PostMapping("/rol")
    public void saveOrUpdate(@RequestBody Rol rol){
        rolService.save(rol);
    }

    @DeleteMapping("/rol/{rolId}")
    public void delete(@PathVariable("rolId") Integer rolId){
        rolService.delete(rolId);
    }

}
