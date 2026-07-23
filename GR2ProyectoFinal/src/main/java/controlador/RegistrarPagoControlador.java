package controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.entity.Cita;
import modelo.entity.Estado;
import modelo.entity.Recepcionista;
import modelo.services.CitaService;
import modelo.services.PagoService;

@WebServlet("/registrarPago")
public class RegistrarPagoControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CitaService citaService = new CitaService();
    private PagoService pagoService = new PagoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("registrarPago".equals(request.getParameter("accion"))) {
            registrarPago(request, response);
        } else {
            consultarCita(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("confirmarPago".equals(request.getParameter("accion"))) {
            confirmarPago(request, response, request.getParameter("monto"));
        } else {
            consultarCita(request, response);
        }
    }

    private void consultarCita(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/consultarCita");
    }

    private void registrarPago(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!esRecepcionista(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Solo recepción puede registrar pagos");
            return;
        }

        Cita cita;
        try {
            cita = citaService.obtenerCita(Long.valueOf(request.getParameter("citaId")));
        } catch (NumberFormatException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Identificador de cita inválido");
            return;
        }

        if (cita == null || cita.getEstado() != Estado.ASISTIDA || !cita.isAtendida()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Solo se puede pagar una cita asistida y atendida");
            return;
        }

        request.getSession().setAttribute("pagoCita", cita);
        request.getRequestDispatcher("/vistas/FormularioPago.jsp").forward(request, response);
    }

    private void confirmarPago(HttpServletRequest request, HttpServletResponse response, String monto) throws ServletException, IOException {
        if (!esRecepcionista(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Solo recepción puede registrar pagos");
            return;
        }

        Cita cita = (Cita) request.getSession().getAttribute("pagoCita");
        if (cita == null || cita.getEstado() != Estado.ASISTIDA || !cita.isAtendida()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No hay una cita asistida seleccionada para el pago");
            return;
        }

        double montoPago;
        try {
            montoPago = Double.parseDouble(monto);
        } catch (NumberFormatException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El monto ingresado no es válido");
            return;
        }
        if (!Double.isFinite(montoPago) || montoPago <= 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El monto debe ser mayor que cero");
            return;
        }

        pagoService.guardarPago(cita, montoPago);
        citaService.cambiarEstadoCita(cita, "Completada");
        request.getSession().removeAttribute("pagoCita");

        request.setAttribute("mensaje", "Pago registrado con éxito. La cita quedó completada");
        request.setAttribute("enlaceContinuar", request.getContextPath() + "/consultarCita");
        request.setAttribute("textoEnlaceContinuar", "Volver a consultar citas");
        request.getRequestDispatcher("/vistas/MensajeInformativo.jsp").forward(request, response);
    }

    private boolean esRecepcionista(HttpServletRequest request) {
        return request.getSession().getAttribute("usuario") instanceof Recepcionista;
    }
}
