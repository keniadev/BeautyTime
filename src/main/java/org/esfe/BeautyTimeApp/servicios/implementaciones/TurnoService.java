package org.esfe.BeautyTimeApp.servicios.implementaciones;

import org.esfe.BeautyTimeApp.modelos.Turno;
import org.esfe.BeautyTimeApp.repositorios.ITurnoRepository;
import  org.esfe.BeautyTimeApp.servicios.interfaces.ITurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {

    @Autowired
    private ITurnoRepository turnoRepository;


    @Override
    public Page<Turno> buscarTodosPaginados(Pageable pageable) {
        return turnoRepository.findAll(pageable);
    }

    @Override
    public List<Turno> obtenerTodos() {
        return turnoRepository.findAll();
    }

    @Override
    public Optional<Turno> buscarPorId(Integer id) {
        return turnoRepository.findById(id);
    }

    @Override
    public Turno crearOEditar(Turno turno) {
        return turnoRepository.save(turno);
    }

    @Override
    public void eliminarPorId(Integer id) {

        turnoRepository.deleteById(id);
    }
}
