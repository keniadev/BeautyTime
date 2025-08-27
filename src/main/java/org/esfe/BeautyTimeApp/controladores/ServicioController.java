package org.esfe.BeautyTimeApp.controladores;

import org.esfe.BeautyTimeApp.modelos.Servicio;
import org.esfe.BeautyTimeApp.servicios.interfaces.IServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    @Autowired
    private IServicioService servicioService;


    @GetMapping
    public ResponseEntity<Page<Servicio>> obtenerTodosPaginados(Pageable pageable) {
        Page<Servicio> servicios = servicioService.buscarTodosPaginados(pageable);
        return ResponseEntity.ok(servicios);
    }


    @GetMapping("/todos")
    public ResponseEntity<List<Servicio>> obtenerTodos() {
        List<Servicio> servicios = servicioService.ObtenerTodos();
        return ResponseEntity.ok(servicios);
    }


    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Servicio>> obtenerPorCategoria(@PathVariable Integer categoriaId) {
        List<Servicio> servicios = servicioService.ObtenerTodos();
        List<Servicio> serviciosFiltrados = servicios.stream()
                .filter(servicio -> servicio.getCategoria().getId().equals(categoriaId))
                .toList();
        return ResponseEntity.ok(serviciosFiltrados);
    }


    @GetMapping("/buscar")
    public ResponseEntity<List<Servicio>> buscarPorNombre(@RequestParam String nombre) {
        List<Servicio> servicios = servicioService.ObtenerTodos();
        List<Servicio> serviciosEncontrados = servicios.stream()
                .filter(servicio -> servicio.getNombreServicio().toLowerCase().contains(nombre.toLowerCase()))
                .toList();
        return ResponseEntity.ok(serviciosEncontrados);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Servicio> obtenerPorId(@PathVariable Integer id) {
        Optional<Servicio> servicio = servicioService.BuscarPorId(id);
        return servicio.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<Servicio> crearServicio(@RequestBody Servicio servicio) {
        Servicio nuevoServicio = servicioService.crearOEditar(servicio);
        return ResponseEntity.ok(nuevoServicio);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Servicio> actualizarServicio(@PathVariable Integer id, @RequestBody Servicio servicio) {
        Optional<Servicio> servicioExistente = servicioService.BuscarPorId(id);
        if (servicioExistente.isPresent()) {
            servicio.setId(id);
            Servicio servicioActualizado = servicioService.crearOEditar(servicio);
            return ResponseEntity.ok(servicioActualizado);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarServicio(@PathVariable Integer id) {
        Optional<Servicio> servicio = servicioService.BuscarPorId(id);
        if (servicio.isPresent()) {
            servicioService.eliminarPorId(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}