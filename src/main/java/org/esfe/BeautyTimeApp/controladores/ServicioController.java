package org.esfe.BeautyTimeApp.controladores;

import org.esfe.BeautyTimeApp.modelos.Servicio;
import org.esfe.BeautyTimeApp.modelos.Categoria;
import org.esfe.BeautyTimeApp.servicios.interfaces.IServicioService;
import org.esfe.BeautyTimeApp.servicios.interfaces.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
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
            servicios = servicioService.obtenerServiciosPorCategoria(categoriaId); // ✅ ahora filtra desde la BD

            // Para mostrar el nombre de la categoría actual en la vista (opcional)
            categoriaService.BuscarPorId(categoriaId)
                    .ifPresent(cat -> model.addAttribute("categoriaActual", cat));
        } else {
            servicios = servicioService.ObtenerTodos(); // muestra todos si no hay categoría seleccionada
        }

        model.addAttribute("servicios", servicios);
        model.addAttribute("servicio", new Servicio());
        model.addAttribute("categorias", categoriaService.ObtenerTodos());

        System.out.println("📦 Servicios mostrados: " + servicios.size());
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
}
