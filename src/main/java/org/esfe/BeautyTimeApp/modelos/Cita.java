package org.esfe.BeautyTimeApp.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "citas")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotBlank
    @Pattern(regexp = "^[0-9]+$", message = "El teléfono solo debe contener números")
    @Size(min = 8, max = 15, message = "El teléfono debe tener entre 8 y 15 dígitos")
    @Column(length = 20)
    private String telefono;

    @NotNull
    @Column(name= "fecha_reserva", nullable = false)
    private LocalDateTime fechaReserva;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cupo_id", nullable = false)
    private Cupo cupo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_cita_id", nullable = false)
    private EstadoCita estadoCita;

    public Cita() {}

    public Cita(Usuario usuario, String telefono, LocalDateTime fechaReserva, Cupo cupo, EstadoCita estadoCita) {
        this.usuario = usuario;
        this.telefono = telefono;
        this.fechaReserva = fechaReserva;
        this.cupo = cupo;
        this.estadoCita = estadoCita;
    }


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public LocalDateTime getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(LocalDateTime fechaReserva) { this.fechaReserva = fechaReserva; }

    public Cupo getCupo() { return cupo; }
    public void setCupo(Cupo cupo) { this.cupo = cupo; }

    public EstadoCita getEstadoCita() { return estadoCita; }
    public void setEstadoCita(EstadoCita estadoCita) { this.estadoCita = estadoCita; }
}
