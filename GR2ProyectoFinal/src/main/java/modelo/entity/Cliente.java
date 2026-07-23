package modelo.entity;

import jakarta.persistence.Entity;

@Entity
public class Cliente extends Usuario {

    public Cliente() {
        super();
    }

    public Cliente(String cedula, String nombre, String correo, String clave) {
        super(cedula, nombre, correo, clave);
    }
}
