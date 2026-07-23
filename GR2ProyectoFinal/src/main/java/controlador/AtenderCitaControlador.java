package controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.entity.Atencion;
import modelo.entity.Cita;
import modelo.entity.HistoriaClinica;
import modelo.entity.Mascota;
import modelo.services.AtencionService;
import modelo.services.CitaService;
import modelo.services.HistoriaClinicaService;

@WebServlet("/atenderCitas")
public class AtenderCitaControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
	private CitaService citaService = new CitaService();
    private HistoriaClinicaService historiaClinicaService = new HistoriaClinicaService();
    private AtencionService atencionService = new AtencionService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if ("marcarAsistenciaDeCita".equals(accion)) {
            marcarAsistenciaDeCita(request, response);
        } else if ("atenderLaCita".equals(accion)) {
            atenderLaCita(request, response);
        } else {
            consultarCita(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if ("crearHistoriaClinica".equals(accion)) {
            crearHistoriaClinica(request, response);
        } else if ("guardarInformacion".equals(accion)) {
            guardarInformacion(request, response, request.getParameter("fecha"), request.getParameter("pesoMascota"),
                    request.getParameter("edadMascota"), request.getParameter("sintomas"), request.getParameter("exploracion"),
                    request.getParameter("diagnostico"), request.getParameter("receta"), request.getParameter("tratamiento"));
        } else if ("abrirHistoriaClinica".equals(accion)) {
            abrirHistoriaClinica(request, response);
        } else if ("registrarNuevaAtencion".equals(accion)) {
            registrarNuevaAtencion(request, response);
        } else if ("guardarAtencion".equals(accion)) {
            guardarAtencion(request, response, request.getParameter("fecha"), request.getParameter("edadMascota"),
                    request.getParameter("pesoMascota"), request.getParameter("sintomas"), request.getParameter("exploracion"),
                    request.getParameter("diagnostico"), request.getParameter("receta"), request.getParameter("tratamiento"));
        } else if ("registrarPago".equals(accion)) {
            registrarPago(request, response);
        } else {
            consultarCita(request, response);
        }
    }

    // ===== 1. Robustez CU02 Atender Citas =====

    private void consultarCita(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/consultarCita");
    }

    private void marcarAsistenciaDeCita(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long citaId = Long.parseLong(request.getParameter("citaId"));
        Cita citaSeleccionada = citaService.obtenerCita(citaId);
        cambiarCitaAAsistida(citaSeleccionada);
        response.sendRedirect(request.getContextPath() + "/consultarCita");
    }

    private void cambiarCitaAAsistida(Cita citaSeleccionada) {
        citaService.cambiarCitaAAsistida(citaSeleccionada);
    }

    private void atenderLaCita(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long citaId = Long.parseLong(request.getParameter("citaId"));
        Cita citaSeleccionada = citaService.obtenerCita(citaId);
        Mascota mascota = citaService.obtenerMascotaCita(citaSeleccionada);
        HttpSession session = request.getSession();
        session.setAttribute("atenderCita", citaSeleccionada);
        session.setAttribute("atenderMascota", mascota);
        HistoriaClinica historiaClinica = historiaClinicaService.obtenerHistoriaClinica(mascota);
        if (historiaClinica != null) {
            session.setAttribute("atenderHistoriaClinica", historiaClinica);
            request.setAttribute("accionConfirmar", "abrirHistoriaClinica");
            mostrarMensajeConfirmacion(request, response, "¿Desea abrir la historia clínica de " + mascota.getNombre() + "?");
        } else {
            request.setAttribute("accionConfirmar", "crearHistoriaClinica");
            mostrarMensajeConfirmacion(request, response, "¿Desea crear la historia clínica de " + mascota.getNombre() + "?");
        }
    }

    private void mostrarMensajeConfirmacion(HttpServletRequest request, HttpServletResponse response, String mensaje) throws ServletException, IOException {
        request.setAttribute("mensaje", mensaje);
        request.getRequestDispatcher("/vistas/MensajeHistoriaClinica.jsp").forward(request, response);
    }

    // ===== 2. Robustez CU02 Crear historia clínica =====

    private void crearHistoriaClinica(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mostrarFormularioAtencion(request, response, "guardarInformacion");
    }

    private void mostrarFormularioAtencion(HttpServletRequest request, HttpServletResponse response, String accionGuardar) throws ServletException, IOException {
        request.setAttribute("accionGuardar", accionGuardar);
        request.getRequestDispatcher("/vistas/FormularioAtencion.jsp").forward(request, response);
    }

    private void guardarInformacion(HttpServletRequest request, HttpServletResponse response, String fecha, String pesoMascota,
            String edadMascota, String sintomas, String exploracion, String diagnostico, String receta, String tratamiento)
            throws ServletException, IOException {
        Mascota mascota = (Mascota) request.getSession().getAttribute("atenderMascota");
        HistoriaClinica historiaClinica = historiaClinicaService.crearHistoriaClinica(mascota);
        registrarAtencion(LocalDate.parse(fecha), Double.parseDouble(pesoMascota), Integer.parseInt(edadMascota), sintomas,
                exploracion, diagnostico, receta, tratamiento, historiaClinica);
        mostrarMensajeInformativo(request, response, "Historia clínica creada con éxito");
    }

    private void registrarAtencion(LocalDate fecha, Double pesoMascota, Integer edadMascota, String sintomas,
            String exploracion, String diagnostico, String receta, String tratamiento, HistoriaClinica historiaClinica) {
        atencionService.registrarAtencion(fecha, pesoMascota, edadMascota, sintomas, exploracion, diagnostico, receta, tratamiento, historiaClinica);
    }

    private void mostrarMensajeInformativo(HttpServletRequest request, HttpServletResponse response, String mensaje) throws ServletException, IOException {
        request.setAttribute("mensaje", mensaje);
        request.setAttribute("enlaceRegistrarPago", request.getContextPath() + "/atenderCitas?accion=registrarPago");
        request.getRequestDispatcher("/vistas/MensajeInformativo.jsp").forward(request, response);
    }

    private void registrarPago(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cita cita = (Cita) request.getSession().getAttribute("atenderCita");
        response.sendRedirect(request.getContextPath() + "/registrarPago?accion=registrarPago&citaId=" + cita.getId());
    }

    // ===== 3. Robustez CU02 Abrir historia clínica =====

    private void abrirHistoriaClinica(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Mascota mascota = (Mascota) session.getAttribute("atenderMascota");
        HistoriaClinica historiaClinica = historiaClinicaService.obtenerHistoriaClinica(mascota);
        session.setAttribute("atenderHistoriaClinica", historiaClinica);
        List<Atencion> listaAtenciones = atencionService.obtenerAtenciones(historiaClinica);
        mostrarHistoriaClinica(request, response, listaAtenciones);
    }

    private void mostrarHistoriaClinica(HttpServletRequest request, HttpServletResponse response, List<Atencion> listaAtenciones) throws ServletException, IOException {
        request.setAttribute("listaAtenciones", listaAtenciones);
        request.getRequestDispatcher("/vistas/FormularioHistoriaClinica.jsp").forward(request, response);
    }

    private void registrarNuevaAtencion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mostrarFormularioAtencion(request, response, "guardarAtencion");
    }

    private void guardarAtencion(HttpServletRequest request, HttpServletResponse response, String fecha, String edadMascota,
            String pesoMascota, String sintomas, String exploracion, String diagnostico, String receta, String tratamiento)
            throws ServletException, IOException {
        HistoriaClinica historiaClinica = (HistoriaClinica) request.getSession().getAttribute("atenderHistoriaClinica");
        guardarNuevaAtencion(LocalDate.parse(fecha), Integer.parseInt(edadMascota), Double.parseDouble(pesoMascota), sintomas,
                exploracion, diagnostico, receta, tratamiento, historiaClinica);
        mostrarMensajeInformativo(request, response, "Atención guardada con éxito");
    }

    private void guardarNuevaAtencion(LocalDate fecha, Integer edadMascota, Double pesoMascota, String sintomas,
            String exploracion, String diagnostico, String receta, String tratamiento, HistoriaClinica historiaClinica) {
        atencionService.guardarNuevaAtencion(fecha, edadMascota, pesoMascota, sintomas, exploracion, diagnostico, receta, tratamiento, historiaClinica);
    }
}
