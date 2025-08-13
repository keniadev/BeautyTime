package org.esfe.BeautyTimeApp.servicios.implementaciones;

import org.esfe.BeautyTimeApp.modelos.EstadoCita;
import org.esfe.BeautyTimeApp.repositorios.IEstadoCitaRepository;
import org.esfe.BeautyTimeApp.servicios.interfaces.IEstadoCitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoCitaService implements IEstadoCitaService {

    @Autowired
    private IEstadoCitaRepository estadoCitaRepository;

    @Override
    public Page<EstadoCita> buscarTodosPaginados(Pageable pageable) {
        return estadoCitaRepository.findAll(pageable);
    }

    @Override
    public List<EstadoCita> ObtenerTodos() {
        return estadoCitaRepository.findAll();
    }

    @Override
    public Optional<EstadoCita> BuscarPorId(Integer id) {
        return estadoCitaRepository.findById(id);
    }

    @Override
    public EstadoCita crearOEditar(EstadoCita estadoCita) {
        return estadoCitaRepository.save(estadoCita);
    }

    @Override
    public void eliminarPorId(Integer id) {
        estadoCitaRepository.deleteById(id);
    }
}
