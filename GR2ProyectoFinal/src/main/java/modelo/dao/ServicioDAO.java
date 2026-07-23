package modelo.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import modelo.entity.Servicio;

public class ServicioDAO {

    private EntityManager em;

    public ServicioDAO() {
        this.em = Persistence.createEntityManagerFactory("persistencia").createEntityManager();
    }

    public List<Servicio> obtenerServicios() {
        return em.createQuery("SELECT s FROM Servicio s", Servicio.class).getResultList();
    }

    public Servicio obtenerServicio(Long id) {
        return em.find(Servicio.class, id);
    }
}
