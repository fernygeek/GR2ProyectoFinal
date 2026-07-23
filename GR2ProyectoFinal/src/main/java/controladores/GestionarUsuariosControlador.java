package controladores;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.entity.Administrador;
import modelo.entity.Cliente;
import modelo.entity.Recepcionista;
import modelo.entity.Usuario;
import modelo.entity.Veterinario;
import modelo.services.UsuarioService;

@WebServlet("/usuarios")
public class GestionarUsuariosControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
	private UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if ("crearUsuario".equals(accion)) {
            crearUsuario(request, response);
        } else if ("actualizar".equals(accion)) {
            actualizar(request, response);
        } else if ("eliminarUsuario".equals(accion)) {
            eliminarUsuario(request, response);
        } else {
            ingresar(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if ("cancelarActualizacion".equals(accion)) {
            cancelarActualizacion(request, response);
        } else if ("actualizarUsuario".equals(accion)) {
            String cedula = request.getParameter("cedula");
            String nombre = request.getParameter("nombre");
            String correo = request.getParameter("correo");
            String clave = request.getParameter("clave");
            actualizarUsuario(request, response, cedula, nombre, correo, clave);
        } else if ("cancelarEliminacion".equals(accion)) {
            cancelarEliminacion(request, response);
        } else if ("confirmarEliminacion".equals(accion)) {
            confirmarEliminacion(request, response);
        } else {
            String cedula = request.getParameter("cedula");
            String nombre = request.getParameter("nombre");
            String correo = request.getParameter("correo");
            String clave = request.getParameter("clave");
            String perfil = request.getParameter("perfil");
            registrar(request, response, cedula, nombre, correo, clave, perfil);
        }
    }

    private void ingresar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Usuario> listaUsuarios = usuarioService.obtenerUsuarios();
        mostrarUsuarios(request, response, listaUsuarios);
    }

    private void mostrarUsuarios(HttpServletRequest request, HttpServletResponse response, List<Usuario> listaUsuarios) throws ServletException, IOException {
        request.setAttribute("listaUsuarios", listaUsuarios);
        request.getRequestDispatcher("/vistas/ListaUsuarios.jsp").forward(request, response);
    }

    private void crearUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mostrarRegistroUsuario(request, response);
    }

    private void mostrarRegistroUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/vistas/FormularioRegistroUsuario.jsp").forward(request, response);
    }

    private void mostrarRegistroUsuario(HttpServletRequest request, HttpServletResponse response, String mensajeErrorYaExisteUsuario) throws ServletException, IOException {
        request.setAttribute("error", mensajeErrorYaExisteUsuario);
        request.getRequestDispatcher("/vistas/FormularioRegistroUsuario.jsp").forward(request, response);
    }

    private void registrar(HttpServletRequest request, HttpServletResponse response, String cedula, String nombre, String correo, String clave, String perfil) throws ServletException, IOException {
        Usuario existente = usuarioService.validarCedulaDuplicada(cedula);
        if (existente != null) {
            mostrarRegistroUsuario(request, response, "Ya existe un usuario con ese número de cédula");
        } else {
            Usuario nuevo = construirUsuario(perfil, cedula, nombre, correo, clave);
            usuarioService.guardarUsuario(nuevo);
            ingresar(request, response);
        }
    }

    private Usuario construirUsuario(String perfil, String cedula, String nombre, String correo, String clave) {
        if ("Veterinario".equals(perfil)) {
            return new Veterinario(cedula, nombre, correo, clave);
        } else if ("Recepcionista".equals(perfil)) {
            return new Recepcionista(cedula, nombre, correo, clave);
        } else if ("Administrador".equals(perfil)) {
            return new Administrador(cedula, nombre, correo, clave);
        }
        return new Cliente(cedula, nombre, correo, clave);
    }

    private void actualizar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cedula = request.getParameter("cedula");
        Usuario usuario = usuarioService.obtenerUsuario(cedula);
        mostrarFormularioActualizacion(request, response, usuario);
    }

    private void mostrarFormularioActualizacion(HttpServletRequest request, HttpServletResponse response, Usuario usuario) throws ServletException, IOException {
        request.setAttribute("usuario", usuario);
        request.getRequestDispatcher("/vistas/FormularioActualizacionUsuario.jsp").forward(request, response);
    }

    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response, String cedula, String nombre, String correo, String clave) throws ServletException, IOException {
        Usuario usuario = usuarioService.obtenerUsuario(cedula);
        usuarioService.actualizarUsuario(usuario, nombre, correo, clave);
        ingresar(request, response);
    }

    private void cancelarActualizacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ingresar(request, response);
    }

    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cedula = request.getParameter("cedula");
        request.setAttribute("cedula", cedula);
        mostrarMensajeConfirmacion(request, response, "¿Confirma la eliminación del usuario?");
    }

    private void mostrarMensajeConfirmacion(HttpServletRequest request, HttpServletResponse response, String mensajeConfirmarEliminacion) throws ServletException, IOException {
        request.setAttribute("mensajeConfirmarEliminacion", mensajeConfirmarEliminacion);
        request.getRequestDispatcher("/vistas/EliminarUsuario.jsp").forward(request, response);
    }

    private void confirmarEliminacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cedula = request.getParameter("cedula");
        Usuario usuario = usuarioService.obtenerUsuario(cedula);
        if (usuarioService.verificarSesionActiva(usuario)) {
            request.setAttribute("error", "No se puede eliminar un usuario con sesión activa");
        } else {
            usuarioService.eliminarUsuario(usuario);
        }
        ingresar(request, response);
    }

    private void cancelarEliminacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ingresar(request, response);
    }
}
