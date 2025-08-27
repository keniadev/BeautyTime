package org.esfe.BeautyTimeApp.controladores;

import org.esfe.BeautyTimeApp.dtos.CupoDTO;
import org.esfe.BeautyTimeApp.modelos.*;
import org.esfe.BeautyTimeApp.servicios.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    @GetMapping("/form")
    public String mostrarFormularioCita(Model model, @RequestParam("usuarioId") Integer usuarioId) {
        Usuario usuario = usuarioService.BuscarPorId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Servicio> servicios = servicioService.ObtenerTodos();

        model.addAttribute("usuario", usuario);
        model.addAttribute("cita", new Cita());
        model.addAttribute("servicios", servicios);

        return "cita/form-cita";
    }


    @PostMapping("/confirmar")
    public String confirmarCita(
            @RequestParam("usuarioId") Integer usuarioId,
            @RequestParam("telefono") String telefono,
            @RequestParam("cupoId") Integer cupoId,
            Model model
    ) {
        try {
            Usuario usuario = usuarioService.BuscarPorId(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

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
            cita.setFechaReserva(LocalDateTime.now()); // <--- Fecha de reserva

            citaService.crearOEditar(cita);

            return "redirect:/citas/mis-citas?usuarioId=" + usuarioId;

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("usuario", usuarioService.BuscarPorId(usuarioId).orElse(null));
            model.addAttribute("servicios", servicioService.ObtenerTodos());
            return "cita/form-cita";
        }


    }
    @GetMapping("/mis-citas")
    public String misCitas(@RequestParam("usuarioId") Integer usuarioId, Model model) {
        model.addAttribute("citas", citaService.listarPorUsuario(usuarioId));
        return "cita/mis-citas";
    }



}
