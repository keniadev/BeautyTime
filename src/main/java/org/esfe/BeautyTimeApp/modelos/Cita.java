package org.esfe.BeautyTimeApp.modelos;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "citas")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombrecliente;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private Integer cupo;

    // Constructor vacío (requerido por JPA)
    public Cita() {
    }

    // Constructor con campos
    public Cita(String nombre, LocalDateTime fecha, Integer cupo) {
        this.nombrecliente = nombre;
        this.fecha = fecha;
        this.cupo = cupo;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombrecliente;
    }

    public void setNombre(String nombre) {
        this.nombrecliente = nombre;
    }

    public LocalDateTime getFechaHora() {
        return fecha;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fecha = fechaHora;
    }

    public Integer getCupo() {
        return cupo;
    }

    public void setCupo(Integer cupo) {
        this.cupo = cupo;
    }
}