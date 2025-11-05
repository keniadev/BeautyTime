package org.esfe.BeautyTimeApp.dtos;

public class CitaDTO {
    private Integer id;
    private String nombreUsuario;
    private String servicioNombre;
    private String telefono;
    private String estado;
    private String turno;
    private String fechaCita;
    private String horaInicio;
    private String horaFin;
    private String fechaReserva;

    public CitaDTO(Integer id, String nombreUsuario, String servicioNombre, String telefono,
                   String estado, String turno, String fechaCita,
                   String horaInicio, String horaFin, String fechaReserva) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.servicioNombre = servicioNombre;
        this.telefono = telefono;
        this.estado = estado;
        this.turno = turno;
        this.fechaCita = fechaCita;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.fechaReserva = fechaReserva;
    }

    public CitaDTO() {}

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getServicioNombre() { return servicioNombre; }
    public void setServicioNombre(String servicioNombre) { this.servicioNombre = servicioNombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    public String getFechaCita() { return fechaCita; }
    public void setFechaCita(String fechaCita) { this.fechaCita = fechaCita; }

    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }

    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }

    public String getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(String fechaReserva) { this.fechaReserva = fechaReserva; }
}
