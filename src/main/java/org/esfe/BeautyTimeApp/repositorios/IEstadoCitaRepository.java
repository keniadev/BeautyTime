package org.esfe.BeautyTimeApp.repositorios;

import org.esfe.BeautyTimeApp.modelos.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IEstadoCitaRepository extends JpaRepository<EstadoCita, Integer> {

    Optional<EstadoCita> findByNombreEstado(String nombreEstado);
}
