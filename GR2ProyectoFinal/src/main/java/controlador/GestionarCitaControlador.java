package controlador;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.entity.Cita;
import modelo.entity.Cliente;
import modelo.entity.Mascota;
import modelo.entity.Servicio;
import modelo.entity.Veterinario;
import modelo.services.CitaService;
import modelo.services.FilaHorario;
import modelo.services.MascotaService;
import modelo.services.ServicioService;
import modelo.services.VeterinarioService;

@WebServlet("/citas")
public class GestionarCitaControlador extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String[] NOMBRES_DIAS = {"Lun", "Mar", "Mié", "Jue", "Vie"};

    private CitaService citaService = new CitaService();
    private MascotaService mascotaService = new MascotaService();
    private ServicioService servicioService = new ServicioService();
    private VeterinarioService veterinarioService = new VeterinarioService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if ("agendarCita".equals(accion)) {
            agendarCita(request, response);
        } else if ("agregarMascota".equals(accion)) {
            agregarMascota(request, response);
        } else if ("reagendar".equals(accion)) {
            reagendar(request, response);
        } else if ("solicitarCancelacion".equals(accion)) {
            solicitarCancelacion(request, response);
        } else {
            ingresar(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if ("guardarMascota".equals(accion)) {
            guardarMascota(request, response, request.getParameter("nombre"), request.getParameter("especie"), request.getParameter("raza"));
        } else if ("cancelarRegistroMascota".equals(accion)) {
            cancelarRegistroMascota(request, response);
        } else if ("seleccionarMascota".equals(accion)) {
            seleccionarMascota(request, response, request.getParameter("mascota"));
        } else if ("seleccionarServicio".equals(accion)) {
            seleccionarServicio(request, response, request.getParameter("servicio"));
        } else if ("seleccionarVeterinario".equals(accion)) {
            seleccionarVeterinario(request, response, request.getParameter("veterinario"));
        } else if ("verSemanaAgendamiento".equals(accion)) {
            verSemanaAgendamiento(request, response);
        } else if ("registrarCita".equals(accion)) {
            registrarCita(request, response, request.getParameter("mascota"), request.getParameter("servicio"),
                    request.getParameter("veterinario"), request.getParameter("horarioSeleccionado"));
        } else if ("confirmarAgendamiento".equals(accion)) {
            confirmarAgendamiento(request, response);
        } else if ("cancelarAgendamiento".equals(accion)) {
            cancelarAgendamiento(request, response);
        } else if ("verSemanaReagendamiento".equals(accion)) {
            verSemanaReagendamiento(request, response);
        } else if ("solicitarReagendar".equals(accion)) {
            solicitarReagendar(request, response, request.getParameter("nuevoHorario"));
        } else if ("cancelarReagendamiento".equals(accion)) {
            cancelarReagendamiento(request, response);
        } else if ("confirmarReagendamiento".equals(accion)) {
            confirmarReagendamiento(request, response);
        } else if ("cancelarSolicitudCancelacion".equals(accion)) {
            cancelarSolicitudCancelacion(request, response);
        } else if ("confirmarCancelacion".equals(accion)) {
            confirmarCancelacion(request, response);
        } else {
            ingresar(request, response);
        }
    }

    private List<String> etiquetasDias(LocalDate semana) {
        List<String> etiquetas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            LocalDate fecha = semana.plusDays(i);
            etiquetas.add(NOMBRES_DIAS[i] + " " + fecha.format(DateTimeFormatter.ofPattern("dd/MM")));
        }
        return etiquetas;
    }

    // ===== 1. Robustez CU01 Listar citas =====

    private void ingresar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cliente cliente = (Cliente) request.getSession().getAttribute("usuario");
        List<Cita> listaCitas = citaService.obtenerCitas(cliente.getCedula());
        mostrarCitas(request, response, listaCitas);
    }

    private void mostrarCitas(HttpServletRequest request, HttpServletResponse response, List<Cita> listaCitas) throws ServletException, IOException {
        request.setAttribute("listaCitas", listaCitas);
        request.getRequestDispatcher("/vistas/ListaCitas.jsp").forward(request, response);
    }

    // ===== 2. Robustez CU01 Agendar cita =====

    private void agendarCita(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        verificarMascotasRegistradas(request, response);
    }

    private void verificarMascotasRegistradas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cliente cliente = (Cliente) request.getSession().getAttribute("usuario");
        List<Mascota> listadoMascotas = mascotaService.obtenerMascotas(cliente.getCedula());
        if (listadoMascotas.isEmpty()) {
            mostrarRegistroMascota(request, response);
        } else {
            mostrarFormularioAgendamiento(request, response, listadoMascotas.toArray(new Mascota[0]));
        }
    }

    private void mostrarRegistroMascota(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/vistas/FormularioRegistroMascota.jsp").forward(request, response);
    }

    private void agregarMascota(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mostrarRegistroMascota(request, response);
    }

    private void guardarMascota(HttpServletRequest request, HttpServletResponse response, String nombre, String especie, String raza) throws ServletException, IOException {
        Cliente cliente = (Cliente) request.getSession().getAttribute("usuario");
        mascotaService.registrarMascota(nombre, especie, raza, cliente);
        agendarCita(request, response);
    }

    private void cancelarRegistroMascota(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ingresar(request, response);
    }

    private void mostrarFormularioAgendamiento(HttpServletRequest request, HttpServletResponse response, Mascota[] listaMascotas) throws ServletException, IOException {
        request.setAttribute("paso", "mascota");
        request.setAttribute("listaMascotas", listaMascotas);
        request.getRequestDispatcher("/vistas/FormularioAgendamiento.jsp").forward(request, response);
    }

    private void seleccionarMascota(HttpServletRequest request, HttpServletResponse response, String mascota) throws ServletException, IOException {
        List<Servicio> listaServicios = servicioService.obtenerServicios();
        request.setAttribute("mascota", mascota);
        mostrarFormularioAgendamiento(request, response, listaServicios.toArray(new Servicio[0]));
    }

    private void mostrarFormularioAgendamiento(HttpServletRequest request, HttpServletResponse response, Servicio[] listaServicios) throws ServletException, IOException {
        request.setAttribute("paso", "servicio");
        request.setAttribute("listaServicios", listaServicios);
        request.getRequestDispatcher("/vistas/FormularioAgendamiento.jsp").forward(request, response);
    }

    private void seleccionarServicio(HttpServletRequest request, HttpServletResponse response, String servicio) throws ServletException, IOException {
        String mascota = request.getParameter("mascota");
        Servicio servicioEntity = servicioService.obtenerServicio(Long.parseLong(servicio));
        List<Veterinario> listaVeterinarios = veterinarioService.obtenerVeterinarios(servicioEntity);
        request.setAttribute("mascota", mascota);
        request.setAttribute("servicio", servicio);
        mostrarFormularioAgendamiento(request, response, listaVeterinarios.toArray(new Veterinario[0]));
    }

    private void mostrarFormularioAgendamiento(HttpServletRequest request, HttpServletResponse response, Veterinario[] listaVeterinarios) throws ServletException, IOException {
        request.setAttribute("paso", "veterinario");
        request.setAttribute("listaVeterinarios", listaVeterinarios);
        request.getRequestDispatcher("/vistas/FormularioAgendamiento.jsp").forward(request, response);
    }

    private void seleccionarVeterinario(HttpServletRequest request, HttpServletResponse response, String veterinario) throws ServletException, IOException {
        String mascota = request.getParameter("mascota");
        String servicio = request.getParameter("servicio");
        LocalDate semanaActual = LocalDate.now().with(DayOfWeek.MONDAY);
        mostrarFormularioAgendamientoHorario(request, response, mascota, servicio, veterinario, semanaActual);
    }

    private void verSemanaAgendamiento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mascota = request.getParameter("mascota");
        String servicio = request.getParameter("servicio");
        String veterinario = request.getParameter("veterinario");
        LocalDate semana = LocalDate.parse(request.getParameter("semana"));
        mostrarFormularioAgendamientoHorario(request, response, mascota, servicio, veterinario, semana);
    }

    private void mostrarFormularioAgendamientoHorario(HttpServletRequest request, HttpServletResponse response, String mascota, String servicio, String veterinario, LocalDate semana) throws ServletException, IOException {
        Veterinario veterinarioEntity = veterinarioService.obtenerVeterinario(veterinario);
        List<FilaHorario> calendario = veterinarioService.obtenerCalendarioSemanal(veterinarioEntity, semana);
        request.setAttribute("mascota", mascota);
        request.setAttribute("servicio", servicio);
        request.setAttribute("veterinario", veterinario);
        request.setAttribute("paso", "horario");
        request.setAttribute("calendario", calendario);
        request.setAttribute("diasSemana", etiquetasDias(semana));
        request.setAttribute("semana", semana.toString());
        request.setAttribute("semanaAnterior", semana.minusWeeks(1).toString());
        request.setAttribute("semanaSiguiente", semana.plusWeeks(1).toString());
        request.getRequestDispatcher("/vistas/FormularioAgendamiento.jsp").forward(request, response);
    }

    private void registrarCita(HttpServletRequest request, HttpServletResponse response, String mascota, String servicio, String veterinario, String horarioSeleccionado) throws ServletException, IOException {
        Mascota mascotaEntity = mascotaService.obtenerMascota(Long.parseLong(mascota));
        Servicio servicioEntity = servicioService.obtenerServicio(Long.parseLong(servicio));
        Veterinario veterinarioEntity = veterinarioService.obtenerVeterinario(veterinario);
        request.setAttribute("mascota", mascota);
        request.setAttribute("servicio", servicio);
        request.setAttribute("veterinario", veterinario);
        request.setAttribute("horarioSeleccionado", horarioSeleccionado);
        request.setAttribute("accionConfirmar", "confirmarAgendamiento");
        request.setAttribute("accionCancelar", "cancelarAgendamiento");
        String mensaje = "¿Confirma la cita de " + mascotaEntity.getNombre() + " para " + servicioEntity.getNombreServicio()
                + " con " + veterinarioEntity.getNombre() + " a la fecha " + horarioSeleccionado + "?";
        mostrarMensajeConfirmacion(request, response, mensaje);
    }

    private void mostrarMensajeConfirmacion(HttpServletRequest request, HttpServletResponse response, String mensaje) throws ServletException, IOException {
        request.setAttribute("mensaje", mensaje);
        request.getRequestDispatcher("/vistas/MensajeConfirmacion.jsp").forward(request, response);
    }

    private void confirmarAgendamiento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mascota = request.getParameter("mascota");
        String servicio = request.getParameter("servicio");
        String veterinario = request.getParameter("veterinario");
        String horarioSeleccionado = request.getParameter("horarioSeleccionado");
        Mascota mascotaEntity = mascotaService.obtenerMascota(Long.parseLong(mascota));
        Servicio servicioEntity = servicioService.obtenerServicio(Long.parseLong(servicio));
        Veterinario veterinarioEntity = veterinarioService.obtenerVeterinario(veterinario);
        Cliente cliente = (Cliente) request.getSession().getAttribute("usuario");
        citaService.guardarCita(mascotaEntity, servicioEntity, veterinarioEntity, LocalDateTime.parse(horarioSeleccionado), cliente);
        mostrarMensajeInformativo(request, response, "Cita agendada con éxito");
    }

    private void mostrarMensajeInformativo(HttpServletRequest request, HttpServletResponse response, String mensaje) throws ServletException, IOException {
        request.setAttribute("mensaje", mensaje);
        request.getRequestDispatcher("/vistas/MensajeInformativo.jsp").forward(request, response);
    }

    private void cancelarAgendamiento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ingresar(request, response);
    }

    // ===== 3. Robustez CU01 Reagendar cita =====

    private void reagendar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String citaId = request.getParameter("citaId");
        LocalDate semanaActual = LocalDate.now().with(DayOfWeek.MONDAY);
        mostrarCalendario(request, response, citaId, semanaActual);
    }

    private void mostrarCalendario(HttpServletRequest request, HttpServletResponse response, String citaId, LocalDate semana) throws ServletException, IOException {
        Cita citaSeleccionada = citaService.obtenerCita(Long.parseLong(citaId));
        Veterinario veterinario = citaService.obtenerVeterinario(citaSeleccionada);
        List<FilaHorario> calendario = veterinarioService.obtenerCalendarioSemanal(veterinario, semana);
        request.setAttribute("citaId", citaId);
        request.setAttribute("calendario", calendario);
        request.setAttribute("diasSemana", etiquetasDias(semana));
        request.setAttribute("semana", semana.toString());
        request.setAttribute("semanaAnterior", semana.minusWeeks(1).toString());
        request.setAttribute("semanaSiguiente", semana.plusWeeks(1).toString());
        request.getRequestDispatcher("/vistas/CalendarioSemanal.jsp").forward(request, response);
    }

    private void verSemanaReagendamiento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String citaId = request.getParameter("citaId");
        LocalDate semana = LocalDate.parse(request.getParameter("semana"));
        mostrarCalendario(request, response, citaId, semana);
    }

    private void solicitarReagendar(HttpServletRequest request, HttpServletResponse response, String nuevoHorario) throws ServletException, IOException {
        String citaId = request.getParameter("citaId");
        request.setAttribute("citaId", citaId);
        request.setAttribute("nuevoHorario", nuevoHorario);
        request.setAttribute("accionConfirmar", "confirmarReagendamiento");
        request.setAttribute("accionCancelar", "cancelarReagendamiento");
        String mensaje = "¿Confirma el reagendamiento a la fecha " + nuevoHorario + "?";
        mostrarMensajeConfirmacion(request, response, mensaje);
    }

    private void cancelarReagendamiento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ingresar(request, response);
    }

    private void confirmarReagendamiento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long citaId = Long.parseLong(request.getParameter("citaId"));
        String nuevoHorario = request.getParameter("nuevoHorario");
        Cita citaSeleccionada = citaService.obtenerCita(citaId);
        LocalDateTime horario = LocalDateTime.parse(nuevoHorario);
        citaSeleccionada.setFecha(horario.toLocalDate());
        citaSeleccionada.setHora(horario.toLocalTime());
        citaService.actualizarFechaCita(citaSeleccionada);
        mostrarMensajeInformativo(request, response, "Reagendamiento realizado con éxito");
    }

    // ===== 4. Robustez CU01 Cancelar cita =====

    private void solicitarCancelacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String citaId = request.getParameter("citaId");
        Cita citaSeleccionada = citaService.obtenerCita(Long.parseLong(citaId));
        request.setAttribute("citaId", citaId);
        request.setAttribute("accionConfirmar", "confirmarCancelacion");
        request.setAttribute("accionCancelar", "cancelarSolicitudCancelacion");
        String mensaje = "¿Confirma cancelar la cita de " + citaSeleccionada.getMascota().getNombre() + " para "
                + citaSeleccionada.getServicio().getNombreServicio() + " a la fecha " + citaSeleccionada.getFecha() + " " + citaSeleccionada.getHora() + "?";
        mostrarMensajeConfirmacion(request, response, mensaje);
    }

    private void cancelarSolicitudCancelacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ingresar(request, response);
    }

    private void confirmarCancelacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String citaId = request.getParameter("citaId");
        Cita citaSeleccionada = citaService.obtenerCita(Long.parseLong(citaId));
        citaService.cancelarCita(citaSeleccionada);
        mostrarMensajeInformativo(request, response, "Cita cancelada con éxito");
    }
}
