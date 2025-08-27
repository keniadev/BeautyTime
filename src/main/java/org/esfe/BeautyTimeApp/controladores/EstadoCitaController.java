package org.esfe.BeautyTimeApp.controladores;

import org.esfe.BeautyTimeApp.modelos.EstadoCita;
import org.esfe.BeautyTimeApp.servicios.interfaces.IEstadoCitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/estados-cita")
public class EstadoCitaController {

    @Autowired
    private IEstadoCitaService estadoCitaService;

    // Listar todos los estados de cita
    @GetMapping
    public String listarEstadosCita(Model model) {
        List<EstadoCita> estados = estadoCitaService.ObtenerTodos();
        model.addAttribute("estadosCita", estados);
        return "/estados-cita/index";
    }

    // Mostrar formulario de creación
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("estadoCita", new EstadoCita());
        return "/estados-cita/crearOeditar";
    }

    // Guardar nuevo estado de cita
    @PostMapping("/guardar")
    public String guardarEstadoCita(@ModelAttribute EstadoCita estadoCita, RedirectAttributes ra) {
        try {
            estadoCitaService.crearOEditar(estadoCita);
            ra.addFlashAttribute("success", "Estado de cita guardado exitosamente!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al guardar el estado de cita: " + e.getMessage());
        }
        return "redirect:/estados-cita";
    }

    // Mostrar formulario de edición
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            EstadoCita estadoCita = estadoCitaService.BuscarPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Estado de cita no encontrado: " + id));
            model.addAttribute("estadoCita", estadoCita);
            return "/estados-cita/crearOeditar";
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Estado de cita no encontrado");
            return "redirect:/estados-cita";
        }
    }

    // Elimina estado de cita
    @GetMapping("/eliminar/{id}")
    public String eliminarEstadoCita(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            estadoCitaService.eliminarPorId(id);
            ra.addFlashAttribute("success", "Estado de cita eliminado exitosamente!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al eliminar el estado de cita: " + e.getMessage());
        }
        return "redirect:/estados-cita";
    }
}