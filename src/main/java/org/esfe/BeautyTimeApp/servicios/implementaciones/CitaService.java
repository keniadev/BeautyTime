package org.esfe.BeautyTimeApp.servicios.implementaciones;

import java.time.format.DateTimeFormatter;

import org.esfe.BeautyTimeApp.dtos.CitaDTO;
import org.esfe.BeautyTimeApp.modelos.Cita;
import org.esfe.BeautyTimeApp.modelos.Cupo;
import org.esfe.BeautyTimeApp.modelos.EstadoCita;
import org.esfe.BeautyTimeApp.modelos.Usuario;
import org.esfe.BeautyTimeApp.repositorios.ICitaRepository;
import org.esfe.BeautyTimeApp.servicios.interfaces.ICitaService;
import org.esfe.BeautyTimeApp.servicios.interfaces.ICupoService;
import org.esfe.BeautyTimeApp.servicios.interfaces.IEstadoCitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CitaService implements ICitaService {
    @Autowired
    private ICitaRepository citaRepository;

    @Autowired
    private ICupoService cupoService;

    @Autowired
    private IEstadoCitaService estadoCitaService;

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

        DateTimeFormatter fechaCitaFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter fechaReservaFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        DateTimeFormatter horaFormat = DateTimeFormatter.ofPattern("HH:mm");

        return citas.stream().map(c -> new CitaDTO(
                c.getId(),
                c.getUsuario().getNombre() + " " + c.getUsuario().getApellido(),
                c.getCupo().getServicio().getNombreServicio(),
                c.getTelefono(),
                c.getEstadoCita().getNombreEstado(),
                c.getCupo().getTurno().getNombreTurno(),
                c.getCupo().getFecha().format(fechaCitaFormat),
                c.getCupo().getTurno().getHoraInicio().format(horaFormat),
                c.getCupo().getTurno().getHoraFin().format(horaFormat),
                c.getFechaReserva().format(fechaReservaFormat)
        )).toList();
    }



    @Transactional
    public Cita crearCitaConCupo(Usuario usuario, String telefono, Cupo cupo) {

        cupoService.ocuparCupo(cupo.getServicio(), cupo.getFecha(), cupo.getTurno());

        EstadoCita estadoPendiente = estadoCitaService.buscarPorNombre("Pendiente")
                .orElseThrow(() -> new RuntimeException("Estado pendiente no encontrado"));

        Cita cita = new Cita();
        cita.setUsuario(usuario);
        cita.setTelefono(telefono);
        cita.setCupo(cupo);
        cita.setEstadoCita(estadoPendiente);
        cita.setFechaReserva(LocalDateTime.now());

        return citaRepository.save(cita);
    }
}