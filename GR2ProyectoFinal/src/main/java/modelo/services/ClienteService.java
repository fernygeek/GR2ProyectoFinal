package modelo.services;

import modelo.dao.ClienteDAO;
import modelo.entity.Cliente;

public class ClienteService {

    private ClienteDAO clienteDAO;

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }

    public boolean verificarCedulaExistente(String cedula) {
        return clienteDAO.obtenerCliente(cedula) != null;
    }

    public void registrarCliente(String cedula, String nombre, String correo, String clave) {
        Cliente cliente = new Cliente(cedula, nombre, correo, clave);
        clienteDAO.guardarCliente(cliente);
    }
}
