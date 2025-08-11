package org.esfe.BeautyTimeApp.servicios.implementaciones;

import org.esfe.BeautyTimeApp.modelos.Usuario;
import org.esfe.BeautyTimeApp.repositorios.IUsuarioRepository;
import org.esfe.BeautyTimeApp.servicios.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class UsuarioService implements IUsuarioService {

    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    @Override
    public Page<Usuario> buscarTodosPaginados(Pageable pageable) {
        return iUsuarioRepository.findAll(pageable);
    }

    @Override
    public List<Usuario> ObtenerTodos() {
        return iUsuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> BuscarPorId(Integer id) {
        return iUsuarioRepository.findById(id);
    }

    @Override
    public Usuario crearOEditar(Usuario usuario) {
        return iUsuarioRepository.save(usuario);
    }

    @Override
    public void eliminarPorId(Integer id) {

     iUsuarioRepository.deleteById(id);

    }
}
