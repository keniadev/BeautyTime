package org.esfe.BeautyTimeApp.servicios.implementaciones;

import org.esfe.BeautyTimeApp.modelos.Servicio;
import org.esfe.BeautyTimeApp.repositorios.IServicioRepository;
import org.esfe.BeautyTimeApp.servicios.interfaces.IServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioService implements IServicioService{

    @Autowired
    private IServicioRepository ServicioRepository;

    @Override
    public Page<Servicio> buscarTodosPaginados(Pageable pageable) {
        return ServicioRepository.findAll(pageable);
    }

    @Override
    public List<Servicio> ObtenerTodos() {

        List<Servicio> servicios = ServicioRepository.findAll();
        System.out.println("📦 Servicios encontrados: " + servicios.size());
        return servicios;
    }


    @Override
    public Optional<Servicio> BuscarPorId(Integer id) {
        return ServicioRepository.findById(id);
    }

    @Override
    public Servicio crearOEditar(Servicio servicio) {
        return ServicioRepository.save(servicio);
    }

    @Override
    public void eliminarPorId(Integer id) {

        ServicioRepository.deleteById(id);
    }

    @Override
    public List<Servicio> obtenerServiciosPorCategoria(Integer categoriaId) {
        return ServicioRepository.findServiciosPorCategoria(categoriaId);
    }


}