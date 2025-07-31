package org.esfe.BeautyTimeApp.modelos;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "cupos")
public class Cupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer disponibilidad;

    @Column(length = 50)
    private String fecha; //


    public Cupo() {
    }


    public Cupo( Integer disponibilidad, String fecha) {
        this.disponibilidad = disponibilidad;
        this.fecha = fecha;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public Integer getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Integer disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getfecha() {
        return fecha;
    }

    public void setfecha(String diaSemana) {
        this.fecha = diaSemana;
    }
}