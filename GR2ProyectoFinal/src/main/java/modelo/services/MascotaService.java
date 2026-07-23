package modelo.services;

import java.util.List;

import modelo.dao.MascotaDAO;
import modelo.entity.Cliente;
import modelo.entity.Mascota;

public class MascotaService {

    private MascotaDAO mascotaDAO;

    public MascotaService() {
        this.mascotaDAO = new MascotaDAO();
    }

    public List<Mascota> obtenerMascotas(String cedulaCliente) {
        return mascotaDAO.obtenerMascotas(cedulaCliente);
    }

    public Mascota obtenerMascota(Long id) {
        return mascotaDAO.obtenerMascota(id);
    }

    public void registrarMascota(String nombre, String especie, String raza, Cliente cliente) {
        Mascota mascota = new Mascota(nombre, especie, raza, cliente);
        mascotaDAO.registrarMascota(mascota);
    }
}
