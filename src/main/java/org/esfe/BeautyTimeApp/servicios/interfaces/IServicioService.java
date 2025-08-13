package org.esfe.BeautyTimeApp.servicios.interfaces;

import org.esfe.BeautyTimeApp.modelos.Servicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IServicioService {

    Page<Servicio> buscarTodosPaginados(Pageable pageable);

    List<Servicio> ObtenerTodos();

    Optional<Servicio> BuscarPorId(Integer id);

    Servicio crearOEditar(Servicio servicio);

    void eliminarPorId(Integer id);
}
