package org.esfe.BeautyTimeApp.controladores;


import org.esfe.BeautyTimeApp.dtos.CupoDTO;
import org.esfe.BeautyTimeApp.modelos.Cupo;
import org.esfe.BeautyTimeApp.modelos.Servicio;
import org.esfe.BeautyTimeApp.modelos.Turno;
import org.esfe.BeautyTimeApp.servicios.interfaces.ICupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.time.LocalTime;

@Controller
public class CupoController {

    @Autowired
    private ICupoService cupoService;

    // ------------------- CRUD -------------------

    @GetMapping("/todos")
    @ResponseBody
    public List<Cupo> obtenerTodos() {
        return cupoService.ObtenerTodos();
    }

    @GetMapping("/cupos/{id}")
    @ResponseBody
    public ResponseEntity<Cupo> obtenerPorId(@PathVariable Integer id) {
        Optional<Cupo> cupo = cupoService.BuscarPorId(id);
        return cupo.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/crear")
    @ResponseBody
    public ResponseEntity<Cupo> crearCupo(@RequestBody Cupo cupo) {
        Cupo nuevoCupo = cupoService.crearOEditar(cupo);
        return ResponseEntity.ok(nuevoCupo);
    }

    @PutMapping("/editar/{id}")
    @ResponseBody
    public ResponseEntity<Cupo> actualizarCupo(@PathVariable Integer id, @RequestBody Cupo cupo) {
        Optional<Cupo> cupoExistente = cupoService.BuscarPorId(id);
        if (cupoExistente.isPresent()) {
            cupo.setId(id);
            Cupo actualizado = cupoService.crearOEditar(cupo);
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<Void> eliminarCupo(@PathVariable Integer id) {
        Optional<Cupo> cupo = cupoService.BuscarPorId(id);
        if (cupo.isPresent()) {
            cupoService.eliminarPorId(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // ------------------- cupos disponibles-------------------

    @GetMapping("/cupos-disponibles")
    @ResponseBody
    public List<CupoDTO> obtenerCuposDisponibles(
            @RequestParam("servicioId") Integer servicioId,
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) {
        Servicio servicio = new Servicio();
        servicio.setId(servicioId);

        return cupoService.obtenerCuposPorServicioYFecha(servicio, fecha)
                .stream()
                // 1. FILTRO DE CANTIDAD: Solo cupos con cantidad > 0
                .filter(c -> c.getCantidad() > 0)

                // 2. FILTRO DE TIEMPO: Oculta turnos que ya pasaron
                .filter(c -> {
                    // Si la fecha seleccionada es HOY
                    if (c.getFecha().isEqual(LocalDate.now())) {
                        // Solo muestra el cupo si la hora de inicio es DESPUÉS de la hora actual
                        return c.getTurno().getHoraInicio().isAfter(LocalTime.now());
                    }
                    // Si la fecha es FUTURA, siempre se muestra (siempre que c.getCantidad() > 0)
                    return true;
                })
                .map(c -> new CupoDTO(
                        c.getId(),
                        c.getTurno().getNombreTurno(),
                        c.getTurno().getHoraInicio(),
                        c.getTurno().getHoraFin()
                ))
                .toList();
    }



    @PostMapping("/confirmar-cupo")
    @ResponseBody
    public String confirmarCupo(
            @RequestParam("servicioId") Integer servicioId,
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("turnoId") Integer turnoId
    ) {
        try {
            Servicio servicio = new Servicio();
            servicio.setId(servicioId);

            Turno turno = new Turno();
            turno.setId(turnoId);

            cupoService.ocuparCupo(servicio, fecha, turno);

            return "Cupo confirmado con éxito ";
        } catch (RuntimeException e) {
            return " " + e.getMessage();
        }
    }

}


