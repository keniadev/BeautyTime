package org.esfe.BeautyTimeApp.servicios.implementaciones;

import org.esfe.BeautyTimeApp.modelos.Categoria;
import org.esfe.BeautyTimeApp.repositorios.ICategoriaRepository;
import org.esfe.BeautyTimeApp.servicios.interfaces.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService implements ICategoriaService {

    @Autowired
    private ICategoriaRepository CategoriaRepository;

    @Override
    public Page<Categoria> buscarTodosPaginados(Pageable pageable) {
        return CategoriaRepository.findAll(pageable);
    }

    @Override
    public List<Categoria> ObtenerTodos() {
        return CategoriaRepository.findAll();
    }

    @Override
    public Optional<Categoria> BuscarPorId(Integer id) {
        return CategoriaRepository.findById(id);
    }

    @Override
    public Categoria crearOEditar(Categoria categoria) {
        return CategoriaRepository.save(categoria);
    }

    @Override
    public void eliminarPorId(Integer id) {
        CategoriaRepository.deleteById(id);
    }
}
