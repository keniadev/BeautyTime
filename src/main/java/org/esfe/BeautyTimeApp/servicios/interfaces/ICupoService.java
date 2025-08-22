package org.esfe.BeautyTimeApp.servicios.interfaces;

import org.esfe.BeautyTimeApp.modelos.Cupo;
import org.esfe.BeautyTimeApp.modelos.Servicio;
import org.esfe.BeautyTimeApp.modelos.Turno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ICupoService {

    Page<Cupo> buscarTodosPaginados(Pageable pageable);

    List<Cupo> ObtenerTodos();

    Optional<Cupo> BuscarPorId(Integer id);

    Cupo crearOEditar(Cupo cupo);

    void eliminarPorId(Integer id);

    List<Cupo> obtenerCuposPorServicioYFecha(Servicio servicio, LocalDate fecha);

    Optional<Cupo> buscarCupoPorServicioFechaTurno(Servicio servicio, LocalDate fecha, Turno turno);

    boolean hayCupoDisponible(Servicio servicio, LocalDate fecha, Turno turno);

    Cupo ocuparCupo(Servicio servicio, LocalDate fecha, Turno turno);
}
