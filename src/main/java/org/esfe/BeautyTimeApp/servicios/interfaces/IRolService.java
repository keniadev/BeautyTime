package org.esfe.BeautyTimeApp.servicios.interfaces;

import org.esfe.BeautyTimeApp.modelos.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IRolService {
    Page<Rol> buscarTodosPaginados(Pageable pageable);

    List<Rol> ObtenerTodos();

    Optional<Rol> BuscarPorId(Integer id);

    Rol crearOEditar(Rol rol);

    void eliminarPorId (Integer rol);
}
