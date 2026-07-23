package modelo.services;

import modelo.dao.HistoriaClinicaDAO;
import modelo.entity.HistoriaClinica;
import modelo.entity.Mascota;

public class HistoriaClinicaService {

    private HistoriaClinicaDAO historiaClinicaDAO;

    public HistoriaClinicaService() {
        this.historiaClinicaDAO = new HistoriaClinicaDAO();
    }

    public HistoriaClinica obtenerHistoriaClinica(Mascota mascota) {
        return historiaClinicaDAO.obtenerHistoriaClinica(mascota);
    }

    public HistoriaClinica crearHistoriaClinica(Mascota mascota) {
        HistoriaClinica historiaClinica = new HistoriaClinica(mascota);
        historiaClinicaDAO.crearHistoriaClinica(historiaClinica);
        return historiaClinica;
    }
}
