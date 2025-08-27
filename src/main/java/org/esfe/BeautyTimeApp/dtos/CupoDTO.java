package org.esfe.BeautyTimeApp.dtos;

import java.time.LocalTime;

public class CupoDTO {
    private Integer id;
    private String nombreTurno;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public CupoDTO(Integer id, String nombreTurno, LocalTime horaInicio, LocalTime horaFin) {
        this.id = id;
        this.nombreTurno = nombreTurno;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombreTurno() { return nombreTurno; }
    public void setNombreTurno(String nombreTurno) { this.nombreTurno = nombreTurno; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
}
