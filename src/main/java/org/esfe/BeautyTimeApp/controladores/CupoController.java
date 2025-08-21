
package org.esfe.BeautyTimeApp.controladores;

import org.esfe.BeautyTimeApp.modelos.Cupo;
import org.esfe.BeautyTimeApp.servicios.interfaces.ICupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/cupos")
public class CupoController {

    @Autowired
    private ICupoService cupoService;


    @GetMapping
    public ResponseEntity<Page<Cupo>> obtenerTodosPaginados(Pageable pageable) {
        Page<Cupo> cupos = cupoService.buscarTodosPaginados(pageable);
        return ResponseEntity.ok(cupos);
    }


    @GetMapping("/todos")
    public ResponseEntity<List<Cupo>> obtenerTodos() {
        List<Cupo> cupos = cupoService.ObtenerTodos();
        return ResponseEntity.ok(cupos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Cupo> obtenerPorId(@PathVariable Integer id) {
        Optional<Cupo> cupo = cupoService.BuscarPorId(id);
        return cupo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<Cupo> crearCupo(@RequestBody Cupo cupo) {
        Cupo nuevoCupo = cupoService.crearOEditar(cupo);
        return ResponseEntity.ok(nuevoCupo);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Cupo> actualizarCupo(@PathVariable Integer id, @RequestBody Cupo cupo) {
        Optional<Cupo> cupoExistente = cupoService.BuscarPorId(id);
        if (cupoExistente.isPresent()) {
            cupo.setId(id);
            Cupo cupoActualizado = cupoService.crearOEditar(cupo);
            return ResponseEntity.ok(cupoActualizado);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCupo(@PathVariable Integer id) {
        Optional<Cupo> cupo = cupoService.BuscarPorId(id);
        if (cupo.isPresent()) {
            cupoService.eliminarPorId(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}