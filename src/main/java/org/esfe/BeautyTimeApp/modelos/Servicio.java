package org.esfe.BeautyTimeApp.modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "servicios")
public class Servicio {
    private Integer id;
    private String nombre;
    private int idCategoria;
}
