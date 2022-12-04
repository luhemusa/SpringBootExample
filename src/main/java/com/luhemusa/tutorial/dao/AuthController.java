package com.luhemusa.tutorial.dao;

import com.luhemusa.tutorial.models.Usuario;
import com.luhemusa.tutorial.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired //inyeccion de dependencia
    private UsuarioDao usuarioDao;
    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String Login(@RequestBody Usuario usuario){

        Usuario userRecuperadoFromBD = usuarioDao.verificarEmailPassword(usuario);
         if(userRecuperadoFromBD != null){// esto retorna true || false
             //devolveremos el token
             String token = jwtUtil.create(String.valueOf(userRecuperadoFromBD.getId()), userRecuperadoFromBD.getEmail()); // ingresamos los datos que queremos agregar al token

             return token;
         }
         return "Error en inicio de sesion";
    }

}
