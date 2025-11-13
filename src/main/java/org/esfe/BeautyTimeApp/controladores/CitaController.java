package org.esfe.BeautyTimeApp.controladores;

import org.esfe.BeautyTimeApp.modelos.*;
import org.esfe.BeautyTimeApp.servicios.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.esfe.BeautyTimeApp.servicios.interfaces.IServicioService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/citas")
public class CitaController {

    @Autowired
    private ICitaService citaService;

    @Autowired
    private ICupoService cupoService;

    @Autowired
    private IServicioService servicioService;

    @Autowired
    private IUsuarioService usuarioService;

    private Usuario getUsuarioLogueado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No hay usuario logueado");
        }
        String correo = ((UserDetails) authentication.getPrincipal()).getUsername();
        return usuarioService.BuscarPorCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));
    }

    // CitaController.java

    @GetMapping("/form")
    public String mostrarFormularioCita(
            @RequestParam(value = "servicioId", required = false) Integer servicioId,
            Model model) {

        Usuario usuario = getUsuarioLogueado();
        model.addAttribute("usuario", usuario);
        model.addAttribute("cita", new Cita());

        if (servicioId != null) {
            // MODO INDIVIDUAL: Se accede desde el catálogo.
            Optional<Servicio> servicioOpt = servicioService.BuscarPorId(servicioId);

            if (servicioOpt.isPresent()) {
                model.addAttribute("servicioSeleccionado", servicioOpt.get());
                // 💡 Redirigir a la NUEVA vista (sin el select de servicio)
                return "cita/form-cita-servicio";
            }
            // Si el servicioId no existe, seguimos al modo global.
        }

        // MODO GLOBAL: Se accede directamente a /citas/form.
        List<Servicio> servicios = servicioService.ObtenerTodos();
        model.addAttribute("servicios", servicios);

        // 💡 Redirigir a la vista ORIGINAL (con el select de servicio)
        return "cita/form-cita";
    }

    @PostMapping("/confirmar")
    @ResponseBody
    public ResponseEntity<String> confirmarCita(
            @RequestParam("telefono") String telefono,
            @RequestParam("cupoId") Integer cupoId
    ) {
        try {
            Usuario usuario = getUsuarioLogueado();
            Cupo cupo = cupoService.BuscarPorId(cupoId)
                    .orElseThrow(() -> new RuntimeException("Cupo no encontrado"));
            citaService.crearCitaConCupo(usuario, telefono, cupo);

            return ResponseEntity.ok("Cita agendada correctamente");

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agendar cita: " + e.getMessage());
        }
    }

    @GetMapping("/mis-citas")
    public String misCitas(Model model) {
        Usuario usuario = getUsuarioLogueado();
        model.addAttribute("citas", citaService.listarPorUsuario(usuario.getId()));
        return "cita/mis-citas";
    }
}
