package org.esfe.BeautyTimeApp.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "estados_cita")
public class EstadoCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50, unique = true)
    private String nombre;




    public EstadoCita() {
    }


    public EstadoCita(String nombre) {
        this.nombre = nombre;

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    @Override
    public String toString() {
        return "EstadoCita{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}