package org.esfe.BeautyTimeApp.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "turnos")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(name = "nombre_turno", nullable = false, length = 50)
    private String nombreTurno;

    @NotNull
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @NotNull
    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    public Turno() {}

    public Turno(String nombreTurno, LocalTime horaInicio, LocalTime horaFin) {
        this.nombreTurno = nombreTurno;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombreTurno() { return nombreTurno; }
    public void setNombreTurno(String nombreTurno) { this.nombreTurno = nombreTurno; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

}
