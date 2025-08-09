package org.esfe.BeautyTimeApp.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "estados_cita")
public class EstadoCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre de la cita es requerido")
    @Column(name = "nombre_cita", nullable = false, length = 50, unique = true)
    private String nombreCita;

    public EstadoCita() {
    }


    public EstadoCita(String nombre) {
        this.nombreCita = nombre;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCita() {
        return nombreCita;
    }

    public void setNombreCita (String nombreCita) {
        this.nombreCita = nombreCita;
    }
}