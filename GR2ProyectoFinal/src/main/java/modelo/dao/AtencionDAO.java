package modelo.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import modelo.entity.Atencion;
import modelo.entity.HistoriaClinica;

public class AtencionDAO {

    private EntityManager em;

    public AtencionDAO() {
        this.em = Persistence.createEntityManagerFactory("persistencia").createEntityManager();
    }

    public void guardarAtencion(Atencion atencion) {
        em.getTransaction().begin();
        em.persist(atencion);
        em.getTransaction().commit();
    }

    public List<Atencion> obtenerAtenciones(HistoriaClinica historiaClinica) {
        return em.createQuery("SELECT a FROM Atencion a WHERE a.historiaClinica = :historiaClinica", Atencion.class)
                .setParameter("historiaClinica", historiaClinica)
                .getResultList();
    }
}
