package com.luhemusa.tutorial.controllers;

import com.luhemusa.tutorial.dao.UsuarioDao;
import com.luhemusa.tutorial.models.Usuario;
import com.luhemusa.tutorial.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired //inyeccion de dependencia
    private UsuarioDao usuarioDao;
    @Autowired //inyeccion de dependencia
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/usuario/{id}") // con la llaves indicamos que va a ser una variable dinamica
    public Usuario getUsuario(@PathVariable Long id){
        return usuarioDao.getUsuario(id);
    }

    private boolean validarToken(String token){
        String idUsuario = jwtUtil.getKey(token);
        return idUsuario != null;
    }

    @RequestMapping(value = "api/usuarios")
    public List<Usuario> getUsuarios(@RequestHeader(value="Authorization") String token){
        //validamos token
        if(!validarToken(token)){
            return null;
        }
       return usuarioDao.getUsuarios();
    }

    @RequestMapping(value = "api/usuario-delete/{id}", method = RequestMethod.DELETE)
    public void  eliminar(@PathVariable Long id, @RequestHeader(value="Authorization") String token){
        if(!validarToken(token)){
            return;
        }
        usuarioDao.eliminar(id);
    }

    @RequestMapping(value = "api/registrar-usuario", method = RequestMethod.POST)
    public void  registrarUsuario(@RequestBody Usuario usuario){
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id); // encryptar password
        String hash = argon2.hash(1, 1024, 1, usuario.getPassword());
        usuario.setPassword(hash);

        usuarioDao.regitrarUsuario(usuario);
    }



}
