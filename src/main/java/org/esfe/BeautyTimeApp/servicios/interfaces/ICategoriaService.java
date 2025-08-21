package org.esfe.BeautyTimeApp.servicios.interfaces;

import org.esfe.BeautyTimeApp.modelos.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {

    Page<Categoria> buscarTodosPaginados(Pageable pageable);

    List<Categoria> ObtenerTodos();

    Optional<Categoria> BuscarPorId(Integer id);

    Categoria crearOEditar(Categoria categoria);

    void eliminarPorId(Integer id);
}

