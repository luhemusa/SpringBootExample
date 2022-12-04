package com.luhemusa.tutorial.dao;

import com.luhemusa.tutorial.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository //Referencia a conexion de la base de datos
@Transactional // como va armar las consultas a SQL
public class UsuarioDaoImp implements UsuarioDao{

    @PersistenceContext
    EntityManager entityManager; // conexion con la basee

    @Override
    public List<Usuario> getUsuarios() {
        String query = "FROM Usuario"; // nombre de la clase
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Usuario getUsuario(Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        return usuario;
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        entityManager.remove(usuario);
    }

    @Override
    public void regitrarUsuario(Usuario usuario) {
        entityManager.merge(usuario);
        //entityManager.merge se utiliza para hacer updates
    }

    @Override
    public Usuario verificarEmailPassword(Usuario usuario) {
        String query = "FROM Usuario WHERE email = :email ";
        List<Usuario> lista =  entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();

        if(lista.isEmpty()){ // si la lista esta vacia devolvemos un false
            return null;
        }

        //primer valor de la lista que obtuvimos de la consulta de usuario en la base de datos
        String passwordHashed = lista.get(0).getPassword();

        //comparacion del hash que viene desde la base con el password que nos pasaron
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if(argon2.verify( passwordHashed, usuario.getPassword())) {
            return lista.get(0);
        }

        return null;
    }

}
