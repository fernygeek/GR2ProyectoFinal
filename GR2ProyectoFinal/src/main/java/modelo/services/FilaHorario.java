package modelo.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class FilaHorario {

    public static class CasillaHorario {

        private LocalDate fecha;
        private LocalTime hora;
        private boolean disponible;

        public CasillaHorario(LocalDate fecha, LocalTime hora, boolean disponible) {
            this.fecha = fecha;
            this.hora = hora;
            this.disponible = disponible;
        }

        public LocalDate getFecha() {
            return fecha;
        }

        public LocalTime getHora() {
            return hora;
        }

        public boolean isDisponible() {
            return disponible;
        }

        public String getHorario() {
            return fecha + "T" + hora;
        }
    }

    private LocalTime hora;
    private List<CasillaHorario> casillas;

    public FilaHorario(LocalTime hora, List<CasillaHorario> casillas) {
        this.hora = hora;
        this.casillas = casillas;
    }

    public LocalTime getHora() {
        return hora;
    }

    public List<CasillaHorario> getCasillas() {
        return casillas;
    }
}
