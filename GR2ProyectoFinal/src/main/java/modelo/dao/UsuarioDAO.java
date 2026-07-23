package modelo.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import modelo.entity.Usuario;

public class UsuarioDAO {

    private EntityManager em;

    public UsuarioDAO() {
        this.em = Persistence.createEntityManagerFactory("persistencia").createEntityManager();
    }

    public Usuario obtenerUsuario(String cedula) {
        return em.find(Usuario.class, cedula);
    }

    public List<Usuario> obtenerUsuarios() {
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }

    public void guardarUsuario(Usuario usuario) {
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
    }

    public void actualizarUsuario(Usuario usuario) {
        em.getTransaction().begin();
        em.merge(usuario);
        em.getTransaction().commit();
    }

    public void eliminarUsuario(Usuario usuario) {
        em.getTransaction().begin();
        Usuario gestionado = em.find(Usuario.class, usuario.getCedula());
        em.remove(gestionado);
        em.getTransaction().commit();
    }
}
