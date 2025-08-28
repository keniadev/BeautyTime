package org.esfe.BeautyTimeApp.controladores;

import org.esfe.BeautyTimeApp.modelos.Categoria;
import org.esfe.BeautyTimeApp.modelos.Servicio;
import org.esfe.BeautyTimeApp.servicios.interfaces.ICategoriaService;
import org.esfe.BeautyTimeApp.servicios.interfaces.IServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/catalogo") // todas las rutas empiezan con /catalogo
public class UsCategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    @Autowired
    private IServicioService servicioService;

    // Lista todas las categorías
    @GetMapping("/categorias")
    public String listarCategorias(Model model) {
        List<Categoria> categorias = categoriaService.ObtenerTodos(); // obtenemos todas las categorías
        model.addAttribute("categorias", categorias); // agregamos al modelo
        return "home/categorias"; // vista HTML que mostrará las categorías
    }

    // Muestra los servicios de una categoría
    @GetMapping("/categoria/{id}")
    public String serviciosPorCategoria(@PathVariable Integer id, Model model) {
        Categoria categoria = categoriaService.BuscarPorId(id).orElse(null);
        if (categoria == null) {
            return "redirect:/catalogo/categorias"; // si la categoría no existe
        }

        List<Servicio> servicios = servicioService.obtenerServiciosPorCategoria(id);

        model.addAttribute("categoria", categoria);
        model.addAttribute("servicios", servicios);

        return "home/servicios";
    }

}
