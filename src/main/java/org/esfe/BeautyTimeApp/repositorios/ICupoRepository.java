package org.esfe.BeautyTimeApp.repositorios;

import jakarta.persistence.LockModeType;
import org.esfe.BeautyTimeApp.modelos.Cupo;
import org.esfe.BeautyTimeApp.modelos.Servicio;
import org.esfe.BeautyTimeApp.modelos.Turno;
import org.esfe.BeautyTimeApp.modelos.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ICupoRepository extends JpaRepository<Cupo, Integer> {


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Cupo c WHERE c.servicio = :servicio AND c.fecha = :fecha AND c.turno = :turno")
    Optional<Cupo> findByServicioAndFechaAndTurnoForUpdate(
            @Param("servicio") Servicio servicio,
            @Param("fecha") LocalDate fecha,
            @Param("turno") Turno turno
    );



    List<Cupo> findByServicioAndFecha(Servicio servicio, LocalDate fecha);

    Optional<Cupo> findByServicioAndFechaAndTurno(Servicio servicio, LocalDate fecha, Turno turno);



}
