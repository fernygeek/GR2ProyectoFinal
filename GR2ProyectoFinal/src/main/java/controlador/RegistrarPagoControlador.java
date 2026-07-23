package controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.entity.Cita;
import modelo.services.CitaService;
import modelo.services.PagoService;

@WebServlet("/registrarPago")
public class RegistrarPagoControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
	private CitaService citaService = new CitaService();
    private PagoService pagoService = new PagoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if ("registrarPago".equals(accion)) {
            registrarPago(request, response);
        } else {
            consultarCita(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if ("confirmarPago".equals(accion)) {
            confirmarPago(request, response, request.getParameter("monto"));
        } else {
            consultarCita(request, response);
        }
    }

    private void consultarCita(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/consultarCita");
    }

    private void registrarPago(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long citaId = Long.parseLong(request.getParameter("citaId"));
        Cita cita = citaService.obtenerCita(citaId);
        request.getSession().setAttribute("pagoCita", cita);
        mostrarFormularioPago(request, response);
    }

    private void mostrarFormularioPago(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/vistas/FormularioPago.jsp").forward(request, response);
    }

    private void confirmarPago(HttpServletRequest request, HttpServletResponse response, String monto) throws ServletException, IOException {
        Cita cita = (Cita) request.getSession().getAttribute("pagoCita");
        guardarPago(cita, Double.parseDouble(monto));
        cambiarEstadoCita(cita, "Completada");
        mensajeInformativo(request, response, "Pago registrado con éxito");
    }

    private void guardarPago(Cita cita, Double monto) {
        pagoService.guardarPago(cita, monto);
    }

    private void cambiarEstadoCita(Cita cita, String estado) {
        citaService.cambiarEstadoCita(cita, estado);
    }

    private void mensajeInformativo(HttpServletRequest request, HttpServletResponse response, String mensaje) throws ServletException, IOException {
        request.setAttribute("mensaje", mensaje);
        request.getRequestDispatcher("/vistas/MensajeInformativo.jsp").forward(request, response);
    }
}
