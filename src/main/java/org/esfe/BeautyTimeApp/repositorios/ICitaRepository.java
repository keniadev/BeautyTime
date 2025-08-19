package org.esfe.BeautyTimeApp.repositorios;

import org.esfe.BeautyTimeApp.modelos.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICitaRepository extends JpaRepository<Cita, Integer> {
}
