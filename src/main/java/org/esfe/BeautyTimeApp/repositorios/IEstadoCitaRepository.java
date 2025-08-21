package org.esfe.BeautyTimeApp.repositorios;

import org.esfe.BeautyTimeApp.modelos.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEstadoCitaRepository extends JpaRepository<EstadoCita, Integer> {
}
