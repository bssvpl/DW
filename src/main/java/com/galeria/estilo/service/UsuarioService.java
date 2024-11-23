package com.galeria.estilo.service;

import com.galeria.estilo.model.DatosPer;
import com.galeria.estilo.model.Usuario;
import com.galeria.estilo.repository.IDatosPer;
import com.galeria.estilo.repository.IUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private IUsuario IUsuario;

    @Autowired
    private IDatosPer IDatosPer;

    public List<Usuario> getUsuarios() {
        return IUsuario.findAll();
    }

    public Optional<Usuario> getUsuario(Integer id) {
        return IUsuario.findById(id);
    }

    public void save(Usuario usuario) {
        IUsuario.save(usuario);
    }

    public void delete(Integer id) {
        IUsuario.deleteById(id);
    }

    public void saveWithDatos(Usuario usuario, DatosPer datosPer) {
        usuario.setDatosPer(datosPer);
        IDatosPer.save(datosPer);
        IUsuario.save(usuario);
    }

    public Usuario validateUser(String username, String password) {
        System.out.println("Validando usuario");
        List<Usuario> usuarios = getUsuarios();
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equals(username) && usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        return null;
    }
}
