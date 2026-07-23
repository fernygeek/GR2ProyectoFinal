package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import modelo.entity.Pago;

public class PagoDAO {

    private EntityManager em;

    public PagoDAO() {
        this.em = Persistence.createEntityManagerFactory("persistencia").createEntityManager();
    }

    public void guardarPago(Pago pago) {
        em.getTransaction().begin();
        em.persist(pago);
        em.getTransaction().commit();
    }
}
