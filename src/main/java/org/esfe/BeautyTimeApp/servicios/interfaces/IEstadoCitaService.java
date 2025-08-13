package org.esfe.BeautyTimeApp.servicios.interfaces;

import org.esfe.BeautyTimeApp.modelos.EstadoCita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IEstadoCitaService {

    Page<EstadoCita> buscarTodosPaginados(Pageable pageable);

    List<EstadoCita> ObtenerTodos();

    Optional<EstadoCita> BuscarPorId(Integer id);

    EstadoCita crearOEditar(EstadoCita estadoCita);

    void eliminarPorId(Integer id);
}
