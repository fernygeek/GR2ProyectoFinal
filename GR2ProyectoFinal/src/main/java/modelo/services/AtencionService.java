package modelo.services;

import java.time.LocalDate;
import java.util.List;

import modelo.dao.AtencionDAO;
import modelo.entity.Atencion;
import modelo.entity.HistoriaClinica;

public class AtencionService {

    private AtencionDAO atencionDAO;

    public AtencionService() {
        this.atencionDAO = new AtencionDAO();
    }

    public void registrarAtencion(LocalDate fecha, Double pesoMascota, Integer edadMascota, String sintomas,
            String exploracion, String diagnostico, String receta, String tratamiento, HistoriaClinica historiaClinica) {
        Atencion atencion = new Atencion(fecha, pesoMascota, edadMascota, sintomas, exploracion, diagnostico, receta, tratamiento, historiaClinica);
        atencionDAO.guardarAtencion(atencion);
    }

    public List<Atencion> obtenerAtenciones(HistoriaClinica historiaClinica) {
        return atencionDAO.obtenerAtenciones(historiaClinica);
    }

    public void guardarNuevaAtencion(LocalDate fecha, Integer edadMascota, Double pesoMascota, String sintomas,
            String exploracion, String diagnostico, String receta, String tratamiento, HistoriaClinica historiaClinica) {
        Atencion atencion = new Atencion(fecha, pesoMascota, edadMascota, sintomas, exploracion, diagnostico, receta, tratamiento, historiaClinica);
        atencionDAO.guardarAtencion(atencion);
    }
}
