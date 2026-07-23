package modelo.dao;

import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import modelo.entity.Servicio;
import modelo.entity.Veterinario;

public class VeterinarioDAO {

    private EntityManager em;

    public VeterinarioDAO() {
        this.em = Persistence.createEntityManagerFactory("persistencia").createEntityManager();
    }

    public Veterinario obtenerVeterinario(String cedula) {
        return em.find(Veterinario.class, cedula);
    }

    public List<Veterinario> obtenerVeterinarios(Servicio servicio) {
        return em.createQuery("SELECT v FROM Veterinario v WHERE :servicio MEMBER OF v.servicios", Veterinario.class)
                .setParameter("servicio", servicio)
                .getResultList();
    }

    public List<LocalTime> obtenerBloquesHorarioLaboral() {
        return List.of(
                LocalTime.of(8, 0), LocalTime.of(9, 0), LocalTime.of(10, 0),
                LocalTime.of(11, 0), LocalTime.of(12, 0), LocalTime.of(13, 0),
                LocalTime.of(14, 0), LocalTime.of(15, 0), LocalTime.of(16, 0)
        );
    }
}
