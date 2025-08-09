package org.esfe.BeautyTimeApp.servicios.interfaces;

import org.esfe.BeautyTimeApp.modelos.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    Page<Usuario> buscarTodosPaginados(Pageable pageable);

    List<Usuario> ObtenerTodos();

    Optional<Usuario> BuscarPorId(Integer id);

    Usuario crearOEditar(Usuario usuario);

    void eliminarPorId(Integer id);

}
