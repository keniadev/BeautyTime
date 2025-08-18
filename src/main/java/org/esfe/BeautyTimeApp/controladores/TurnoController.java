package org.esfe.BeautyTimeApp.controladores;

import org.esfe.BeautyTimeApp.modelos.Rol;
import org.esfe.BeautyTimeApp.modelos.Turno;
import org.esfe.BeautyTimeApp.servicios.interfaces.ITurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/Turnos")
public class TurnoController {

    @Autowired
    private ITurnoService turnoService;

    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){

        int currentPage = page.orElse( 1) - 1; // si no esta setreado se asigna el 0
        int pageSize = size.orElse(5); //tamaño de la pagina se asigan 5
        Pageable pageable = PageRequest.of(currentPage,pageSize);

        Page<Turno> roles = turnoService.buscarTodosPaginados(pageable);
        model.addAttribute("turnos",roles );

        int totalPages = roles.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "turno/index";
    }
}
