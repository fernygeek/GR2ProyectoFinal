package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import modelo.entity.HistoriaClinica;
import modelo.entity.Mascota;

public class HistoriaClinicaDAO {

    private EntityManager em;

    public HistoriaClinicaDAO() {
        this.em = Persistence.createEntityManagerFactory("persistencia").createEntityManager();
    }

    public HistoriaClinica obtenerHistoriaClinica(Mascota mascota) {
        try {
            return em.createQuery("SELECT h FROM HistoriaClinica h WHERE h.mascota = :mascota", HistoriaClinica.class)
                    .setParameter("mascota", mascota)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void crearHistoriaClinica(HistoriaClinica historiaClinica) {
        em.getTransaction().begin();
        em.persist(historiaClinica);
        em.getTransaction().commit();
    }
}
