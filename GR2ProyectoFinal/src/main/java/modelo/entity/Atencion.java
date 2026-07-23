package modelo.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Atencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private Double pesoMascota;
    private Integer edadMascota;
    private String sintomas;
    private String exploracion;
    private String diagnostico;
    private String receta;
    private String tratamiento;

    @ManyToOne
    private HistoriaClinica historiaClinica;

    public Atencion() {
    }

    public Atencion(LocalDate fecha, Double pesoMascota, Integer edadMascota, String sintomas, String exploracion,
            String diagnostico, String receta, String tratamiento, HistoriaClinica historiaClinica) {
        this.fecha = fecha;
        this.pesoMascota = pesoMascota;
        this.edadMascota = edadMascota;
        this.sintomas = sintomas;
        this.exploracion = exploracion;
        this.diagnostico = diagnostico;
        this.receta = receta;
        this.tratamiento = tratamiento;
        this.historiaClinica = historiaClinica;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getPesoMascota() {
        return pesoMascota;
    }

    public void setPesoMascota(Double pesoMascota) {
        this.pesoMascota = pesoMascota;
    }

    public Integer getEdadMascota() {
        return edadMascota;
    }

    public void setEdadMascota(Integer edadMascota) {
        this.edadMascota = edadMascota;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getExploracion() {
        return exploracion;
    }

    public void setExploracion(String exploracion) {
        this.exploracion = exploracion;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getReceta() {
        return receta;
    }

    public void setReceta(String receta) {
        this.receta = receta;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public HistoriaClinica getHistoriaClinica() {
        return historiaClinica;
    }

    public void setHistoriaClinica(HistoriaClinica historiaClinica) {
        this.historiaClinica = historiaClinica;
    }
}
