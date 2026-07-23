package controlador;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.entity.Cita;
import modelo.services.CitaService;

@WebServlet("/consultarCita")
public class ConsultarCitaControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
	private CitaService citaService = new CitaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        consultarCita(request, response);
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
        request.getRequestDispatcher("/vistas/FormularioConsultarCita.jsp").forward(request, response);
    }

    private void solicitarBuscarCitas(HttpServletRequest request, HttpServletResponse response, String cedula) throws ServletException, IOException {
        List<Cita> listaCitasAgendadas = citaService.consultarCitasAgendadas(cedula);
        if (listaCitasAgendadas.isEmpty()) {
            mostrarConsulta(request, response, "El cliente no tiene citas agendadas");
        } else {
            mostrarConsulta(request, response, listaCitasAgendadas);
        }
    }

    private void mostrarConsulta(HttpServletRequest request, HttpServletResponse response, List<Cita> listaCitasAgendadas) throws ServletException, IOException {
        request.setAttribute("listaCitasAgendadas", listaCitasAgendadas);
        request.getRequestDispatcher("/vistas/FormularioConsultarCita.jsp").forward(request, response);
    }

    private void mostrarConsulta(HttpServletRequest request, HttpServletResponse response, String mensajeNoExistenCitasRegistradas) throws ServletException, IOException {
        request.setAttribute("mensajeNoExistenCitasRegistradas", mensajeNoExistenCitasRegistradas);
        request.getRequestDispatcher("/vistas/FormularioConsultarCita.jsp").forward(request, response);
    }
}
