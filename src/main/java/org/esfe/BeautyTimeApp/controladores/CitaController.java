package org.esfe.BeautyTimeApp.controladores;

import org.esfe.BeautyTimeApp.modelos.*;
import org.esfe.BeautyTimeApp.servicios.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private IEstadoCitaService estadoCitaService;

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
    public String mostrarFormularioCita(Model model) {
        Usuario usuario = getUsuarioLogueado();
        List<Servicio> servicios = servicioService.ObtenerTodos();

        model.addAttribute("usuario", usuario);
        model.addAttribute("cita", new Cita());
        model.addAttribute("servicios", servicios);

        return "cita/form-cita";
    }

    @PostMapping("/confirmar")
    public String confirmarCita(
            @RequestParam("telefono") String telefono,
            @RequestParam("cupoId") Integer cupoId,
            Model model
    ) {
        try {
            Usuario usuario = getUsuarioLogueado();
            Cupo cupo = cupoService.BuscarPorId(cupoId)
                    .orElseThrow(() -> new RuntimeException("Cupo no encontrado"));


            cupoService.ocuparCupo(cupo.getServicio(), cupo.getFecha(), cupo.getTurno());

            EstadoCita estadoPendiente = estadoCitaService.buscarPorNombre("Pendiente")
                    .orElseThrow(() -> new RuntimeException("Estado pendiente no encontrado"));


            Cita cita = new Cita();
            cita.setUsuario(usuario);
            cita.setTelefono(telefono);
            cita.setCupo(cupo);
            cita.setEstadoCita(estadoPendiente);
            cita.setFechaReserva(LocalDateTime.now());

            citaService.crearOEditar(cita);

            return "redirect:/citas/mis-citas";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("servicios", servicioService.ObtenerTodos());
            return "cita/form-cita";
        }
    }


    @GetMapping("/mis-citas")
    public String misCitas(Model model) {
        Usuario usuario = getUsuarioLogueado();
        model.addAttribute("citas", citaService.listarPorUsuario(usuario.getId()));
        return "cita/mis-citas";
    }
}
