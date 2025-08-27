package org.esfe.BeautyTimeApp.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "estados_cita")
public class EstadoCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre del estado es requerido")
    @Column(name = "nombre_cita", nullable = false, length = 50, unique = true)
    private String nombreEstado;

    public EstadoCita() {
    }
    public EstadoCita(String nombre) {
        this.nombreEstado = nombre;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado (String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
}