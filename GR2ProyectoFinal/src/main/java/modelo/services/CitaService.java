package modelo.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import modelo.dao.CitaDAO;
import modelo.entity.Cita;
import modelo.entity.Cliente;
import modelo.entity.Estado;
import modelo.entity.Mascota;
import modelo.entity.Servicio;
import modelo.entity.Veterinario;

public class CitaService {

    private CitaDAO citaDAO;

    public CitaService() {
        this.citaDAO = new CitaDAO();
    }

    public Cita obtenerCita(Long id) {
        return citaDAO.obtenerCita(id);
    }

    public List<Cita> obtenerCitas(String cedulaCliente) {
        return citaDAO.obtenerCitas(cedulaCliente);
    }

    public List<Cita> consultarCitasAgendadas(String cedula) {
        return citaDAO.obtenerCitas(cedula);
    }

    public void guardarCita(Mascota mascota, Servicio servicio, Veterinario veterinario, LocalDateTime horarioSeleccionado, Cliente cliente) {
        Cita cita = new Cita(horarioSeleccionado.toLocalDate(), horarioSeleccionado.toLocalTime(), cliente, mascota, servicio, veterinario, Estado.PENDIENTE);
        citaDAO.guardarCita(cita);
    }

    public List<LocalDateTime> obtenerCitasOcupadas(Veterinario veterinario, LocalDate semana) {
        return citaDAO.obtenerCitasOcupadas(veterinario, semana);
    }

    public Veterinario obtenerVeterinario(Cita citaSeleccionada) {
        return citaSeleccionada.getVeterinario();
    }

    public void actualizarFechaCita(Cita citaSeleccionada) {
        citaDAO.actualizarFechaCita(citaSeleccionada);
    }

    public void cancelarCita(Cita citaSeleccionada) {
        citaSeleccionada.setEstado(Estado.CANCELADA);
        citaDAO.cancelarCita(citaSeleccionada);
    }

    public void cambiarCitaAAsistida(Cita citaSeleccionada) {
        citaSeleccionada.setEstado(Estado.ASISTIDA);
        citaDAO.actualizarCita(citaSeleccionada);
    }

    public Mascota obtenerMascotaCita(Cita citaSeleccionada) {
        return citaSeleccionada.getMascota();
    }

    public void cambiarEstadoCita(Cita cita, String estado) {
        cita.setEstado(Estado.valueOf(estado.toUpperCase()));
        citaDAO.actualizarCita(cita);
    }
}
