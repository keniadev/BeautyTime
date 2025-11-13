package org.esfe.BeautyTimeApp.controladores;

import jakarta.validation.Valid;
import org.esfe.BeautyTimeApp.modelos.Rol;
import org.esfe.BeautyTimeApp.modelos.Usuario;
import org.esfe.BeautyTimeApp.servicios.interfaces.IRolService;
import org.esfe.BeautyTimeApp.servicios.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IRolService rolService;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size ){
        int currentPage = page.orElse(1) -1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage,pageSize);

        Page<Usuario> usuarios = usuarioService.buscarTodosPaginados(pageable);
        model.addAttribute("usuarios", usuarios);

        int totalPages = usuarios.getTotalPages();
        if(totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "usuario/index";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolService.ObtenerTodos());
        return "usuario/create";
    }


    // package org.esfe.BeautyTimeApp.controladores; (resto de imports)
// ...

    @PostMapping("/save")
    public String save(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes attributes){

        // 1. Asignar el rol de "Cliente" (ID=2) automáticamente
        final Integer ROL_CLIENTE_ID = 2;
        Optional<Rol> rolClienteOptional = rolService.BuscarPorId(ROL_CLIENTE_ID);

        if (rolClienteOptional.isEmpty()) {
            // Esto es un error grave, el rol 'Cliente' no existe en la base de datos
            attributes.addFlashAttribute("error", "Error: No se pudo encontrar el Rol de Cliente (ID: " + ROL_CLIENTE_ID + ").");
            return "redirect:/usuarios/create";
        }

        // Establecer el rol de Cliente al usuario
        usuario.setRol(rolClienteOptional.get());


        // 2. Validación de campos (la validación de campos del modelo sigue siendo necesaria)
        if (result.hasErrors()) {
            // Ya no necesitamos agregar "roles" al modelo
            // model.addAttribute("roles", rolService.ObtenerTodos());

            String errores = result.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));

            model.addAttribute("errorMensaje", errores);
            return "usuario/create"; // Vuelve al formulario
        }

        // 3. Guardar usuario
        usuarioService.crearOEditar(usuario);
        attributes.addFlashAttribute("msg", "Usuario creado correctamente");
        return "redirect:/usuarios";
    }

}
