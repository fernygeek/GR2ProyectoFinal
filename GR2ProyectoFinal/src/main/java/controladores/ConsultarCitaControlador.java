package controladores;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.entity.Cita;
import modelo.entity.Recepcionista;
import modelo.entity.Usuario;
import modelo.entity.Veterinario;
import modelo.services.CitaService;

@WebServlet("/consultarCita")
public class ConsultarCitaControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
	private CitaService citaService = new CitaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("marcarAsistencia".equals(request.getParameter("accion"))) {
            marcarAsistencia(request, response);
        } else {
            consultarCita(request, response);
        }
    }

    private void marcarAsistencia(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!(request.getSession().getAttribute("usuario") instanceof Recepcionista)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Solo recepción puede marcar la asistencia");
            return;
        }
        try {
            Cita cita = citaService.obtenerCita(Long.valueOf(request.getParameter("citaId")));
            if (cita == null || cita.getEstado() != modelo.entity.Estado.PENDIENTE) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "La cita no está pendiente");
                return;
            }
            citaService.cambiarCitaAAsistida(cita);
            request.getSession().setAttribute("mensajeAsistencia", "Cita marcada como asistida correctamente");
            response.sendRedirect(request.getContextPath() + "/consultarCita");
        } catch (NumberFormatException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Identificador de cita inválido");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cedula = request.getParameter("cedula");
        solicitarBuscarCitas(request, response, cedula);
    }

    private void consultarCita(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mostrarConsulta(request, response);
    }

    private void mostrarConsulta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        agregarDatosRol(request);
        Object mensajeAsistencia = request.getSession().getAttribute("mensajeAsistencia");
        if (mensajeAsistencia != null) {
            request.setAttribute("mensajeAsistencia", mensajeAsistencia);
            request.getSession().removeAttribute("mensajeAsistencia");
        }
        request.getRequestDispatcher("/vistas/FormularioConsultarCita.jsp").forward(request, response);
    }

    private void solicitarBuscarCitas(HttpServletRequest request, HttpServletResponse response, String cedula) throws ServletException, IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        List<Cita> listaCitasAgendadas;
        if (usuario instanceof Veterinario) {
            listaCitasAgendadas = citaService.consultarCitasAgendadas(cedula, (Veterinario) usuario);
        } else {
            listaCitasAgendadas = citaService.consultarCitasAgendadas(cedula);
        }
        if (listaCitasAgendadas.isEmpty()) {
            String mensaje = usuario instanceof Veterinario
                    ? "El cliente no tiene citas asignadas a este veterinario"
                    : "El cliente no tiene citas agendadas";
            mostrarConsulta(request, response, mensaje);
        } else {
            mostrarConsulta(request, response, listaCitasAgendadas);
        }
    }

    private void mostrarConsulta(HttpServletRequest request, HttpServletResponse response, List<Cita> listaCitasAgendadas) throws ServletException, IOException {
        agregarDatosRol(request);
        request.setAttribute("listaCitasAgendadas", listaCitasAgendadas);
        request.getRequestDispatcher("/vistas/FormularioConsultarCita.jsp").forward(request, response);
    }

    private void mostrarConsulta(HttpServletRequest request, HttpServletResponse response, String mensajeNoExistenCitasRegistradas) throws ServletException, IOException {
        agregarDatosRol(request);
        request.setAttribute("mensajeNoExistenCitasRegistradas", mensajeNoExistenCitasRegistradas);
        request.getRequestDispatcher("/vistas/FormularioConsultarCita.jsp").forward(request, response);
    }

    private void agregarDatosRol(HttpServletRequest request) {
        Object usuario = request.getSession().getAttribute("usuario");
        request.setAttribute("esVeterinario", usuario instanceof Veterinario);
        request.setAttribute("esRecepcionista", usuario instanceof Recepcionista);
    }
}
