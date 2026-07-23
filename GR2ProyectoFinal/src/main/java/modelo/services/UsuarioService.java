package modelo.services;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import modelo.dao.UsuarioDAO;
import modelo.entity.Usuario;

public class UsuarioService {

    private static final Set<String> sesionesActivas = ConcurrentHashMap.newKeySet();

    private UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario autenticar(String cedula, String clave) {
        Usuario usuario = usuarioDAO.obtenerUsuario(cedula);
        if (usuario != null && usuario.getClave().equals(clave)) {
            return usuario;
        }
        return null;
    }

    public List<Usuario> obtenerUsuarios() {
        return usuarioDAO.obtenerUsuarios();
    }

    public Usuario obtenerUsuario(String cedula) {
        return usuarioDAO.obtenerUsuario(cedula);
    }

    public Usuario validarCedulaDuplicada(String cedula) {
        return usuarioDAO.obtenerUsuario(cedula);
    }

    public void guardarUsuario(Usuario usuario) {
        usuarioDAO.guardarUsuario(usuario);
    }

    public void actualizarUsuario(Usuario usuario, String nombre, String correo, String clave) {
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setClave(clave);
        usuarioDAO.actualizarUsuario(usuario);
    }

    public void eliminarUsuario(Usuario usuario) {
        usuarioDAO.eliminarUsuario(usuario);
    }

    public boolean verificarSesionActiva(Usuario usuario) {
        return sesionesActivas.contains(usuario.getCedula());
    }

    public void registrarSesionActiva(String cedula) {
        sesionesActivas.add(cedula);
    }

    public void eliminarSesionActiva(String cedula) {
        sesionesActivas.remove(cedula);
    }
}
