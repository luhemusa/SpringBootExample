package com.luhemusa.tutorial.dao;

import com.luhemusa.tutorial.models.Usuario;

import java.util.List;

public interface UsuarioDao {

    List<Usuario> getUsuarios();

    Usuario getUsuario(Long id);

    void eliminar(Long id);

    void regitrarUsuario(Usuario usuario);


    Usuario verificarEmailPassword(Usuario usuario);
}
