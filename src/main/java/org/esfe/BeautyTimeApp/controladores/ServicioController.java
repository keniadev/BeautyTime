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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private IServicioService servicioService;

    @Autowired(required = false)
    private ICategoriaService categoriaService;

    // Carpeta donde se guardarán las imágenes
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/servicios/";

    // ⚙️ Límite de tamaño (10 MB)
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 👈 esta línea es la clave

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
    public String guardarServicio(
            @ModelAttribute Servicio servicio,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            Model model,
            RedirectAttributes redirectAttributes) {

        try {
            if (!imagenFile.isEmpty()) {

                // 🔍 Validar tamaño del archivo
                if (imagenFile.getSize() > MAX_FILE_SIZE) {
                    model.addAttribute("servicio", servicio);
                    model.addAttribute("categorias", categoriaService.ObtenerTodos());
                    model.addAttribute("error", "⚠️ La imagen supera el tamaño máximo permitido (10 MB).");
                    return "servicio/crearOeditar"; // 👈 se queda en la misma vista
                }

                // 📂 Guardar imagen
                String nombreArchivo = imagenFile.getOriginalFilename();
                Path destino = Paths.get(UPLOAD_DIR + nombreArchivo);
                Files.createDirectories(destino.getParent());
                Files.write(destino, imagenFile.getBytes());

                servicio.setImagenUrl("/uploads/servicios/" + nombreArchivo);
            }

            servicioService.crearOEditar(servicio);
            redirectAttributes.addFlashAttribute("success", "✅ Servicio guardado correctamente.");
            return "redirect:/servicios/gestionar";

        } catch (IOException e) {
            model.addAttribute("servicio", servicio);
            model.addAttribute("categorias", categoriaService.ObtenerTodos());
            model.addAttribute("error", " Error al subir la imagen. Intenta nuevamente.");
            return "servicio/crearOeditar.html";
        }
    }


    @GetMapping("/eliminar/{id}")
    public String eliminarServicioFromView(@PathVariable Integer id) {
        servicioService.eliminarPorId(id);
        return "redirect:/servicios/gestionar";
    }
}
