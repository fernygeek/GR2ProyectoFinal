package modelo.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import modelo.dao.VeterinarioDAO;
import modelo.entity.Servicio;
import modelo.entity.Veterinario;
import modelo.services.FilaHorario.CasillaHorario;

public class VeterinarioService {

    private VeterinarioDAO veterinarioDAO;
    private CitaService citaService;

    public VeterinarioService() {
        this.veterinarioDAO = new VeterinarioDAO();
        this.citaService = new CitaService();
    }

    public Veterinario obtenerVeterinario(String cedula) {
        return veterinarioDAO.obtenerVeterinario(cedula);
    }

    public List<Veterinario> obtenerVeterinarios(Servicio servicio) {
        return veterinarioDAO.obtenerVeterinarios(servicio);
    }

    public List<LocalTime> obtenerBloquesHorarioLaboral() {
        return veterinarioDAO.obtenerBloquesHorarioLaboral();
    }

    public List<FilaHorario> obtenerCalendarioSemanal(Veterinario veterinario, LocalDate semana) {
        List<LocalTime> bloques = obtenerBloquesHorarioLaboral();
        List<LocalDateTime> ocupados = citaService.obtenerCitasOcupadas(veterinario, semana);
        List<FilaHorario> filas = new ArrayList<>();
        for (LocalTime hora : bloques) {
            List<CasillaHorario> casillas = new ArrayList<>();
            for (int dia = 0; dia < 5; dia++) {
                LocalDate fecha = semana.plusDays(dia);
                LocalDateTime slot = LocalDateTime.of(fecha, hora);
                boolean disponible = !ocupados.contains(slot) && slot.isAfter(LocalDateTime.now());
                casillas.add(new CasillaHorario(fecha, hora, disponible));
            }
            filas.add(new FilaHorario(hora, casillas));
        }
        return filas;
    }
}
