package modelo.services;

import java.util.List;

import modelo.dao.ServicioDAO;
import modelo.entity.Servicio;

public class ServicioService {

    private ServicioDAO servicioDAO;

    public ServicioService() {
        this.servicioDAO = new ServicioDAO();
    }

    public List<Servicio> obtenerServicios() {
        return servicioDAO.obtenerServicios();
    }

    public Servicio obtenerServicio(Long id) {
        return servicioDAO.obtenerServicio(id);
    }
}
