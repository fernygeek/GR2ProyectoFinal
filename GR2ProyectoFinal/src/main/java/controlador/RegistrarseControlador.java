package controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.entity.Cliente;
import modelo.services.ClienteService;
import modelo.services.UsuarioService;

@WebServlet("/registrarse")
public class RegistrarseControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
	private ClienteService clienteService = new ClienteService();
    private UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        registrarse(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if ("cancelarRegistro".equals(accion)) {
            cancelarRegistro(request, response);
        } else {
            String cedula = request.getParameter("cedula");
            String nombre = request.getParameter("nombre");
            String correo = request.getParameter("correo");
            String clave = request.getParameter("clave");
            guardarRegistro(request, response, cedula, nombre, correo, clave);
        }
    }

    private void registrarse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mostrarRegistroCliente(request, response);
    }

    private void mostrarRegistroCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/vistas/FormularioRegistroCliente.jsp").forward(request, response);
    }

    private void mostrarRegistroCliente(HttpServletRequest request, HttpServletResponse response, String mensajeErrorUsuarioDuplicado) throws ServletException, IOException {
        request.setAttribute("error", mensajeErrorUsuarioDuplicado);
        request.getRequestDispatcher("/vistas/FormularioRegistroCliente.jsp").forward(request, response);
    }

    private void guardarRegistro(HttpServletRequest request, HttpServletResponse response, String cedula, String nombre, String correo, String clave) throws ServletException, IOException {
        if (clienteService.verificarCedulaExistente(cedula)) {
            mostrarRegistroCliente(request, response, "Ya existe un usuario registrado con esa cédula");
        } else {
            clienteService.registrarCliente(cedula, nombre, correo, clave);
            crearSesion(request, new Cliente(cedula, nombre, correo, clave));
            ingresar(request, response);
        }
    }

    private void crearSesion(HttpServletRequest request, Cliente cliente) {
        request.getSession().setAttribute("usuario", cliente);
        usuarioService.registrarSesionActiva(cliente.getCedula());
    }

    private void cancelarRegistro(HttpServletRequest request, HttpServletResponse response) throws IOException {
        entrar(request, response);
    }

    private void entrar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/autenticarse");
    }

    private void ingresar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/citas");
    }
}
