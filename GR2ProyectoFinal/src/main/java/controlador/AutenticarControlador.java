package controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.entity.Administrador;
import modelo.entity.Cliente;
import modelo.entity.Usuario;
import modelo.entity.Veterinario;
import modelo.services.UsuarioService;

@WebServlet({"/autenticarse", "/login"})
public class AutenticarControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
	private UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if ("salir".equals(accion)) {
            salir(request, response);
        } else {
            entrar(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if ("cancelarCerrarSesion".equals(accion)) {
            cancelarCerrarSesion(request, response);
        } else if ("confirmarCerrarSesion".equals(accion)) {
            confirmarCerrarSesion(request, response);
        } else {
            String cedula = request.getParameter("cedula");
            String clave = request.getParameter("clave");
            solicitarAcceso(request, response, cedula, clave);
        }
    }

    private void entrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mostrarInicioSesion(request, response);
    }

    private void mostrarInicioSesion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/vistas/FormularioInicioSesion.jsp").forward(request, response);
    }

    private void mostrarInicioSesion(HttpServletRequest request, HttpServletResponse response, String mensajeErrorEnAutenticacion) throws ServletException, IOException {
        request.setAttribute("error", mensajeErrorEnAutenticacion);
        request.getRequestDispatcher("/vistas/FormularioInicioSesion.jsp").forward(request, response);
    }

    private void solicitarAcceso(HttpServletRequest request, HttpServletResponse response, String cedula, String clave) throws ServletException, IOException {
        Usuario usuario = usuarioService.autenticar(cedula, clave);
        if (usuario != null) {
            request.setAttribute("usuario", usuario);
            crearSesion(request);
            ingresar(request, response);
        } else {
            mostrarInicioSesion(request, response, "Cédula o clave incorrectas");
        }
    }

    private void crearSesion(HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getAttribute("usuario");
        HttpSession session = request.getSession();
        session.setAttribute("usuario", usuario);
        usuarioService.registrarSesionActiva(usuario.getCedula());
    }

    private void ingresar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        response.sendRedirect(request.getContextPath() + rutaPanel(usuario));
    }

    private String rutaPanel(Usuario usuario) {
        if (usuario instanceof Cliente) {
            return "/citas";
        } else if (usuario instanceof Administrador) {
            return "/usuarios";
        } else if (usuario instanceof Veterinario) {
            return "/consultarCita";
        }
        return "/consultarCita";
    }

    private void salir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mostrarCerrarSesion(request, response, "¿Está seguro de cerrar sesión?");
    }

    private void mostrarCerrarSesion(HttpServletRequest request, HttpServletResponse response, String mensajeSalir) throws ServletException, IOException {
        request.setAttribute("mensajeSalir", mensajeSalir);
        request.getRequestDispatcher("/vistas/CerrarSesion.jsp").forward(request, response);
    }

    private void cancelarCerrarSesion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ingresar(request, response);
    }

    private void confirmarCerrarSesion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        mostrarInicioSesion(request, response);
    }
}
