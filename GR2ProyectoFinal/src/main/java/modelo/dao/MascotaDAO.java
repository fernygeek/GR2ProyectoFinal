package modelo.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import modelo.entity.Mascota;

public class MascotaDAO {

    private EntityManager em;

    public MascotaDAO() {
        this.em = Persistence.createEntityManagerFactory("persistencia").createEntityManager();
    }

    public Mascota obtenerMascota(Long id) {
        return em.find(Mascota.class, id);
    }

    public List<Mascota> obtenerMascotas(String cedulaCliente) {
        return em.createQuery("SELECT m FROM Mascota m WHERE m.cliente.cedula = :cedula", Mascota.class)
                .setParameter("cedula", cedulaCliente)
                .getResultList();
    }

    public void registrarMascota(Mascota mascota) {
        em.getTransaction().begin();
        em.persist(mascota);
        em.getTransaction().commit();
    }
}
