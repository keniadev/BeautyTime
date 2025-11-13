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

    @GetMapping("/form")
    public String mostrarFormularioCita(
        @RequestParam(value = "servicioId", required = false) Integer servicioId,
        Model model) {

            if (servicioId != null) {
                // Modo INDIVIDUAL: Si se pasó un ID, buscamos el servicio y lo pasamos.
                servicioService.BuscarPorId(servicioId)
                        .ifPresent(servicio -> model.addAttribute("servicioSeleccionado", servicio));
            }
        Usuario usuario = getUsuarioLogueado();
        List<Servicio> servicios = servicioService.ObtenerTodos();

        model.addAttribute("usuario", usuario);
        model.addAttribute("cita", new Cita());
        model.addAttribute("servicios", servicios);

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
