package org.esfe.BeautyTimeApp.repositorios;

import org.esfe.BeautyTimeApp.modelos.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IServicioRepository extends JpaRepository<Servicio, Integer> {
    List<Servicio> findByCategoriaId(Integer categoriaId);
}
