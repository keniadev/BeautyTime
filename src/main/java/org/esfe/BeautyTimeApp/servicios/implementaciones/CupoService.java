package org.esfe.BeautyTimeApp.servicios.implementaciones;

import org.esfe.BeautyTimeApp.modelos.Cupo;
import org.esfe.BeautyTimeApp.repositorios.ICupoRepository;
import org.esfe.BeautyTimeApp.servicios.interfaces.ICupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CupoService implements ICupoService{
    @Autowired
    private ICupoRepository icupoRepository;

    @Override
    public Page<Cupo> buscarTodosPaginados(Pageable pageable) {
        return icupoRepository.findAll(pageable);
    }

    @Override
    public List<Cupo> ObtenerTodos() {
        return icupoRepository.findAll();
    }

    @Override
    public Optional<Cupo> BuscarPorId(Integer id) {
        return icupoRepository.findById(id);
    }

    @Override
    public Cupo crearOEditar(Cupo cupo) {
        return icupoRepository.save(cupo);
    }

    @Override
    public void eliminarPorId(Integer id) {

        icupoRepository.deleteById(id);
    }
}


