package org.esfe.BeautyTimeApp.repositorios;

import org.esfe.BeautyTimeApp.modelos.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICitaRepository extends JpaRepository<Cita, Integer> {

    List<Cita> findByUsuarioId(Integer usuarioId);
    @Query("""
        SELECT c FROM Cita c
        JOIN FETCH c.usuario u
        JOIN FETCH c.cupo cu
        JOIN FETCH cu.turno t
        JOIN FETCH cu.servicio s
        JOIN FETCH c.estadoCita ec
        WHERE u.id = :usuarioId
    """)
    List<Cita> findAllWithDetailsByUsuarioId(@Param("usuarioId") Integer usuarioId);

}
