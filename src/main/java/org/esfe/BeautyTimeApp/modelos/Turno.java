package org.esfe.BeautyTimeApp.modelos;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "turnos")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_turno", nullable = false, length = 50)
    private String nombreTurno;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @OneToMany(mappedBy = "turno", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Cupo> cupos = new ArrayList<>();

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

    public List<Cupo> getCupos() { return cupos; }
    public void setCupos(List<Cupo> cupos) { this.cupos = cupos; }
}
