package modelo.entity;

import jakarta.persistence.Entity;

@Entity
public class Administrador extends Usuario {

    public Administrador() {
        super();
    }

    public Administrador(String cedula, String nombre, String correo, String clave) {
        super(cedula, nombre, correo, clave);
    }
}
