package controladores;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import modelo.entity.Administrador;
import modelo.entity.Cliente;
import modelo.entity.Recepcionista;
import modelo.entity.Servicio;
import modelo.entity.Usuario;
import modelo.entity.Veterinario;
import modelo.entity.Servicio.TipoServicio;
import modelo.services.ClienteService;
import modelo.services.MascotaService;
import modelo.services.UsuarioService;

@WebListener
public class Sesion implements HttpSessionListener, ServletContextListener {

    private UsuarioService usuarioService = new UsuarioService();

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        Object atributo = se.getSession().getAttribute("usuario");
        if (atributo instanceof Usuario) {
            usuarioService.eliminarSesionActiva(((Usuario) atributo).getCedula());
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        List<Usuario> usuariosExistentes = usuarioService.obtenerUsuarios();
        if (!usuariosExistentes.isEmpty()) {
            if (usuarioService.obtenerUsuario("3000000001") == null) {
                usuarioService.guardarUsuario(new Recepcionista(
                        "3000000001", "Daniela López", "daniela.lopez@petcare.com", "recep123"));
            }
            return;
        }

        Administrador admin = new Administrador("0000000001", "Admin Principal", "admin@correo.com", "admin123");
        usuarioService.guardarUsuario(admin);

        Recepcionista recepcionista = new Recepcionista(
                "3000000001", "Daniela López", "daniela.lopez@petcare.com", "recep123");
        usuarioService.guardarUsuario(recepcionista);

        Veterinario vet1 = new Veterinario("1000000001", "Dr. Carlos Mendoza", "carlos.mendoza@petcare.com", "vet123");
        Veterinario vet2 = new Veterinario("1000000002", "Dra. Ana Torres", "ana.torres@petcare.com", "vet123");
        Veterinario vet3 = new Veterinario("1000000003", "Dr. Luis Ramirez", "luis.ramirez@petcare.com", "vet123");
        usuarioService.guardarUsuario(vet1);
        usuarioService.guardarUsuario(vet2);
        usuarioService.guardarUsuario(vet3);

        ClienteService clienteService = new ClienteService();
        clienteService.registrarCliente("2000000001", "Cliente Demo", "cliente.demo@correo.com", "cliente123");

        MascotaService mascotaService = new MascotaService();
        Cliente clienteDemo = (Cliente) usuarioService.obtenerUsuario("2000000001");
        mascotaService.registrarMascota("Firulais", "Perro", "Labrador", clienteDemo);

        // No existe un Service/DAO para crear Servicio ni para asignar la relación
        // Veterinario-Servicio, así que se persisten aquí directamente con un
        // EntityManager, igual que hace cada DAO del proyecto.
        EntityManager em = Persistence.createEntityManagerFactory("persistencia").createEntityManager();
        em.getTransaction().begin();

        Servicio consulta = new Servicio("Consulta General", TipoServicio.CONSULTA);
        Servicio vacunacion = new Servicio("Vacunación", TipoServicio.VACUNACION);
        Servicio desparasitacion = new Servicio("Desparasitación", TipoServicio.DESPARASITACION);
        Servicio fisioterapia = new Servicio("Fisioterapia", TipoServicio.FISIOTERAPIA);
        em.persist(consulta);
        em.persist(vacunacion);
        em.persist(desparasitacion);
        em.persist(fisioterapia);

        Veterinario vet1Gestionado = em.find(Veterinario.class, vet1.getCedula());
        vet1Gestionado.setServicios(List.of(consulta, vacunacion));

        Veterinario vet2Gestionado = em.find(Veterinario.class, vet2.getCedula());
        vet2Gestionado.setServicios(List.of(consulta, desparasitacion, fisioterapia));

        Veterinario vet3Gestionado = em.find(Veterinario.class, vet3.getCedula());
        vet3Gestionado.setServicios(List.of(consulta, vacunacion, desparasitacion, fisioterapia));

        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
