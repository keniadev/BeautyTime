package org.esfe.BeautyTimeApp.controladores;

import org.esfe.BeautyTimeApp.modelos.Servicio;
import org.esfe.BeautyTimeApp.modelos.Categoria;
import org.esfe.BeautyTimeApp.servicios.interfaces.IServicioService;
import org.esfe.BeautyTimeApp.servicios.interfaces.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private IServicioService servicioService;

    @Autowired(required = false)
    private ICategoriaService categoriaService;

    // Endpoints para vistas HTML (deben estar antes de los endpoints con @PathVariable)
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("servicio", new Servicio());
        if (categoriaService != null) {
            List<Categoria> categorias = categoriaService.ObtenerTodos();
            model.addAttribute("categorias", categorias);
        } else {
            model.addAttribute("categorias", List.of());
        }
        return "servicio/crearOeditar.html";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Optional<Servicio> servicio = servicioService.BuscarPorId(id);
        if (servicio.isPresent()) {
            model.addAttribute("servicio", servicio.get());
            if (categoriaService != null) {
                List<Categoria> categorias = categoriaService.ObtenerTodos();
                model.addAttribute("categorias", categorias);
            } else {
                model.addAttribute("categorias", List.of());
            }
            return "servicio/crearOeditar.html";
        }
        return "redirect:/servicios/gestionar";
    }

    @GetMapping("/gestionar")
    public String gestionarServicios(Model model,
                                     @RequestParam(required = false) Integer categoriaId) {
        List<Servicio> servicios;

        if (categoriaId != null) {
            servicios = servicioService.ObtenerTodos().stream()
                    .filter(servicio -> servicio.getCategoria().getId().equals(categoriaId))
                    .toList();
            if (categoriaService != null) {
                Optional<Categoria> categoria = categoriaService.BuscarPorId(categoriaId);
                categoria.ifPresent(cat -> model.addAttribute("categoriaActual", cat));
            }
        } else {
            servicios = servicioService.ObtenerTodos();
        }

        model.addAttribute("servicios", servicios);
        model.addAttribute("servicio", new Servicio());

        if (categoriaService != null) {
            List<Categoria> categorias = categoriaService.ObtenerTodos();
            model.addAttribute("categorias", categorias);
        } else {
            model.addAttribute("categorias", List.of());
        }

        return "servicio/gestion-servicios";
    }

    @PostMapping("/guardar")
    public String guardarServicio(@ModelAttribute Servicio servicio) {
        servicioService.crearOEditar(servicio);
        return "redirect:/servicios/gestionar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarServicioFromView(@PathVariable Integer id) {
        servicioService.eliminarPorId(id);
        return "redirect:/servicios/gestionar";
    }

    // Endpoints API REST (estos pueden estar después)
    @GetMapping
    @ResponseBody
    public ResponseEntity<Page<Servicio>> obtenerTodosPaginados(Pageable pageable) {
        Page<Servicio> servicios = servicioService.buscarTodosPaginados(pageable);
        return ResponseEntity.ok(servicios);
    }

    @GetMapping("/todos")
    @ResponseBody
    public ResponseEntity<List<Servicio>> obtenerTodos() {
        List<Servicio> servicios = servicioService.ObtenerTodos();
        return ResponseEntity.ok(servicios);
    }

    @GetMapping("/categoria/{categoriaId}")
    @ResponseBody
    public ResponseEntity<List<Servicio>> obtenerPorCategoria(@PathVariable Integer categoriaId) {
        List<Servicio> servicios = servicioService.ObtenerTodos();
        List<Servicio> serviciosFiltrados = servicios.stream()
                .filter(servicio -> servicio.getCategoria().getId().equals(categoriaId))
                .toList();
        return ResponseEntity.ok(serviciosFiltrados);
    }

    @GetMapping("/buscar")
    @ResponseBody
    public ResponseEntity<List<Servicio>> buscarPorNombre(@RequestParam String nombre) {
        List<Servicio> servicios = servicioService.ObtenerTodos();
        List<Servicio> serviciosEncontrados = servicios.stream()
                .filter(servicio -> servicio.getNombreServicio().toLowerCase().contains(nombre.toLowerCase()))
                .toList();
        return ResponseEntity.ok(serviciosEncontrados);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Servicio> obtenerPorId(@PathVariable Integer id) {
        Optional<Servicio> servicio = servicioService.BuscarPorId(id);
        return servicio.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Servicio> crearServicio(@RequestBody Servicio servicio) {
        Servicio nuevoServicio = servicioService.crearOEditar(servicio);
        return ResponseEntity.ok(nuevoServicio);
    }

    @PutMapping("/{id}")
    @ResponseBody
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
    @ResponseBody
    public ResponseEntity<Void> eliminarServicio(@PathVariable Integer id) {
        Optional<Servicio> servicio = servicioService.BuscarPorId(id);
        if (servicio.isPresent()) {
            servicioService.eliminarPorId(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}