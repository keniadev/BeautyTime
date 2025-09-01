package org.esfe.BeautyTimeApp.servicios.implementaciones;


import org.esfe.BeautyTimeApp.dtos.CitaDTO;
import org.esfe.BeautyTimeApp.modelos.Cita;
import org.esfe.BeautyTimeApp.repositorios.ICitaRepository;
import org.esfe.BeautyTimeApp.servicios.interfaces.ICitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CitaService implements ICitaService {
    @Autowired
    private ICitaRepository citaRepository;

    @Override
    public Page<Cita> buscarTodosPaginados(Pageable pageable) {
        return citaRepository.findAll(pageable);
    }

    @Override
    public List<Cita> obtenerTodos() {
        return citaRepository.findAll();
    }

    @Override
    public Optional<Cita> buscarPorId(Integer id) {
        return citaRepository.findById(id);
    }

    @Override
    public Cita crearOEditar(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    public void eliminarPorId(Integer id) {
        citaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaDTO> listarPorUsuario(Integer usuarioId) {

        List<Cita> citas = citaRepository.findAllWithDetailsByUsuarioId(usuarioId);

        return citas.stream().map(c -> new CitaDTO(
                c.getId(),
                c.getUsuario().getNombre() + " " + c.getUsuario().getApellido(),
                c.getCupo().getServicio().getNombreServicio(),
                c.getTelefono(),
                c.getEstadoCita().getNombreEstado(),
                c.getCupo().getTurno().getNombreTurno(),
                c.getCupo().getTurno().getHoraInicio().toString(),
                c.getCupo().getTurno().getHoraFin().toString()
        )).toList();
    }
}