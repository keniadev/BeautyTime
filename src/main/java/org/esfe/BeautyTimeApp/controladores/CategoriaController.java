package org.esfe.BeautyTimeApp.controladores;

import org.esfe.BeautyTimeApp.modelos.Categoria;
import org.esfe.BeautyTimeApp.servicios.interfaces.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    // Muestra todas las categorías - RETORNA LA VISTA DE CATEGORÍAS
    @GetMapping
    public String listarCategorias(Model model) {
        List<Categoria> categorias = categoriaService.ObtenerTodos();
        model.addAttribute("categorias", categorias);
        model.addAttribute("categoria", new Categoria()); // Para el formulario
        return "categorias/index"; // Retorna a la vista categorias/index.html
    }

    // Formulario de creación de una categoría
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/crearOeditar";
    }

    // Guardar una nueva o editar una categoría existente
    @PostMapping("/guardar")
    public String guardarCategoria(@ModelAttribute Categoria categoria, RedirectAttributes ra) {
        try {
            categoriaService.crearOEditar(categoria);
            ra.addFlashAttribute("success", "Categoría guardada exitosamente!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al guardar la categoría.");
        }
        return "redirect:/categorias"; // Redirige al listado de categorías
    }

    // Formulario para editar una categoría
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model) {
        categoriaService.BuscarPorId(id).ifPresent(categoria -> model.addAttribute("categoria", categoria));
        return "categorias/crearOeditar";
    }

    // Elimina una categoría por id
    @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            categoriaService.eliminarPorId(id);
            ra.addFlashAttribute("success", "Categoría eliminada exitosamente!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al eliminar la categoría.");
        }
        return "redirect:/categorias"; // Redirige al listado de categorías
    }
}