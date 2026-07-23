package modelo.entity;

import jakarta.persistence.Entity;

@Entity
public class Recepcionista extends Usuario {

    public Recepcionista() {
        super();
    }

    public Recepcionista(String cedula, String nombre, String correo, String clave) {
        super(cedula, nombre, correo, clave);
    }
}
