package modelo.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import modelo.entity.Cita;
import modelo.entity.Veterinario;

public class CitaDAO {

    private EntityManager em;

    public CitaDAO() {
        this.em = Persistence.createEntityManagerFactory("persistencia").createEntityManager();
    }

    public Cita obtenerCita(Long id) {
        return em.find(Cita.class, id);
    }

    public List<Cita> obtenerCitas(String cedulaCliente) {
        return em.createQuery("SELECT c FROM Cita c WHERE c.cliente.cedula = :cedula", Cita.class)
                .setParameter("cedula", cedulaCliente)
                .getResultList();
    }

    public void guardarCita(Cita cita) {
        em.getTransaction().begin();
        em.persist(cita);
        em.getTransaction().commit();
    }

    public List<LocalDateTime> obtenerCitasOcupadas(Veterinario veterinario, LocalDate semana) {
        List<Cita> citas = em.createQuery(
                "SELECT c FROM Cita c WHERE c.veterinario = :veterinario AND c.fecha BETWEEN :inicio AND :fin AND c.estado <> modelo.entities.EstadoCita.CANCELADA",
                Cita.class)
                .setParameter("veterinario", veterinario)
                .setParameter("inicio", semana)
                .setParameter("fin", semana.plusDays(6))
                .getResultList();
        return citas.stream()
                .map(c -> LocalDateTime.of(c.getFecha(), c.getHora()))
                .collect(Collectors.toList());
    }

    public void actualizarFechaCita(Cita cita) {
        actualizarCita(cita);
    }

    public void cancelarCita(Cita cita) {
        actualizarCita(cita);
    }

    public void actualizarCita(Cita cita) {
        em.getTransaction().begin();
        em.merge(cita);
        em.getTransaction().commit();
    }
}
