package org.esfe.BeautyTimeApp.controladores;

import org.esfe.BeautyTimeApp.modelos.Categoria;
import org.esfe.BeautyTimeApp.modelos.Servicio;
import org.esfe.BeautyTimeApp.servicios.interfaces.ICategoriaService;
import org.esfe.BeautyTimeApp.servicios.interfaces.IServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ICategoriaService categoriaService;

    @Autowired
    private IServicioService servicioService;

    @GetMapping("/")
    public String index(Model model) {
        List<Categoria> categorias = categoriaService.ObtenerTodos();
        List<Servicio> servicios = servicioService.ObtenerTodos();

        model.addAttribute("categorias", categorias);
        model.addAttribute("servicios", servicios);
        return "home/index";
    }
}
