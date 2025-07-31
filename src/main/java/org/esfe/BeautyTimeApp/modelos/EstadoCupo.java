package org.esfe.BeautyTimeApp.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "estados_cupos")
public class EstadoCupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50, unique = true)
    private String nombre;




    public EstadoCupo() {
    }


    public EstadoCupo(String nombre ) {
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


}