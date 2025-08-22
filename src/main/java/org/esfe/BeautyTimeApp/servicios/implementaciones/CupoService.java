package org.esfe.BeautyTimeApp.servicios.implementaciones;

import org.esfe.BeautyTimeApp.modelos.Cupo;
import org.esfe.BeautyTimeApp.modelos.Servicio;
import org.esfe.BeautyTimeApp.modelos.Turno;
import org.esfe.BeautyTimeApp.repositorios.ICupoRepository;
import org.esfe.BeautyTimeApp.servicios.interfaces.ICupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    @Override
    public List<Cupo> obtenerCuposPorServicioYFecha(Servicio servicio, LocalDate fecha) {
        return icupoRepository.findByServicioAndFecha(servicio, fecha);
    }

    @Override
    public Optional<Cupo> buscarCupoPorServicioFechaTurno(Servicio servicio, LocalDate fecha, Turno turno) {
        return icupoRepository.findByServicioAndFechaAndTurno(servicio, fecha, turno);
    }

    @Override
    public boolean hayCupoDisponible(Servicio servicio, LocalDate fecha, Turno turno) {
        Optional<Cupo> cupoOpt = buscarCupoPorServicioFechaTurno(servicio, fecha, turno);
        return cupoOpt.isPresent() && cupoOpt.get().getCantidad() > 0;
    }

    @Override
    @Transactional
    public Cupo ocuparCupo(Servicio servicio, LocalDate fecha, Turno turno) {
        Optional<Cupo> cupoOpt = icupoRepository.findByServicioAndFechaAndTurnoForUpdate(servicio, fecha, turno);
        if(cupoOpt.isPresent()) {
            Cupo cupo = cupoOpt.get();
            if(cupo.getCantidad() > 0) {
                cupo.setCantidad(cupo.getCantidad() - 1);
                return icupoRepository.save(cupo);
            } else {
                throw new RuntimeException("No hay cupos disponibles para este turno");
            }
        } else {
            throw new RuntimeException("Cupo no encontrado para este servicio, fecha y turno");
        }
    }
}


