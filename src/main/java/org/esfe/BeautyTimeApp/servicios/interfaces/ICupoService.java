package org.esfe.BeautyTimeApp.servicios.interfaces;

import org.esfe.BeautyTimeApp.modelos.Cupo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICupoService {

    Page<Cupo> buscarTodosPaginados(Pageable pageable);

    List<Cupo> ObtenerTodos();

    Optional<Cupo> BuscarPorId(Integer id);

    Cupo crearOEditar(Cupo cupo);

    void eliminarPorId(Integer id);
}
