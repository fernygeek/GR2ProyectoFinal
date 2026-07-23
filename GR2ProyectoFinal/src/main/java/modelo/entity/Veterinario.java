package modelo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

@Entity
public class Veterinario extends Usuario {

    @ManyToMany
    private List<Servicio> servicios = new ArrayList<>();

    public Veterinario() {
        super();
    }

    public Veterinario(String cedula, String nombre, String correo, String clave) {
        super(cedula, nombre, correo, clave);
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }
}
