package org.esfe.BeautyTimeApp.servicios.interfaces;

import org.esfe.BeautyTimeApp.modelos.Turno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ITurnoService {
    Page<Turno> buscarTodosPaginados(Pageable pageable);

    List<Turno> obtenerTodos();

    Optional<Turno> buscarPorId(Integer id);

    Turno crearOEditar(Turno turno);

    void eliminarPorId(Integer id);

}
