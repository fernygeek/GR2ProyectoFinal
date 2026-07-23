package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import modelo.entity.Cliente;

public class ClienteDAO {

    private EntityManager em;

    public ClienteDAO() {
        this.em = Persistence.createEntityManagerFactory("persistencia").createEntityManager();
    }

    public Cliente obtenerCliente(String cedula) {
        return em.find(Cliente.class, cedula);
    }

    public void guardarCliente(Cliente cliente) {
        em.getTransaction().begin();
        em.persist(cliente);
        em.getTransaction().commit();
    }
}
