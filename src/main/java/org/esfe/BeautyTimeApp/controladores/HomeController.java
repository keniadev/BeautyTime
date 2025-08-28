package org.esfe.BeautyTimeApp.controladores;

import org.esfe.BeautyTimeApp.modelos.Categoria;
import org.esfe.BeautyTimeApp.modelos.Servicio;
import org.esfe.BeautyTimeApp.servicios.interfaces.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping

    public String index(Model model) {
        try {
            List<Categoria> categorias = categoriaService.ObtenerTodos();

            if (categorias == null || categorias.isEmpty()) {
                categorias = getCategoriasMock();
            }

            model.addAttribute("categorias", categorias);

        } catch (Exception e) {
            model.addAttribute("categorias", getCategoriasMock());
        }

        return "home/index"; // 👈 SOLO muestra categorías en la página principal
    }

    @GetMapping("/login")
    public String login() {
        return "usuario/login"; // ruta: templates/usuario/login.html
    }
    // Mock temporal de categorías
    private List<Categoria> getCategoriasMock() {
        return Arrays.asList(
                new Categoria(1, "Uñas"),
                new Categoria(2, "Cabello"),
                new Categoria(3, "Maquillaje"),
                new Categoria(4, "Skincare"),
                new Categoria(5, "Depilación")
        );
    }
}
