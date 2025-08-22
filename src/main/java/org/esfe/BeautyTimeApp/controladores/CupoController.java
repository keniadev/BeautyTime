package org.esfe.BeautyTimeApp.controladores;


import org.esfe.BeautyTimeApp.dtos.CupoDTO;
import org.esfe.BeautyTimeApp.modelos.Cupo;
import org.esfe.BeautyTimeApp.modelos.Servicio;
import org.esfe.BeautyTimeApp.modelos.Turno;
import org.esfe.BeautyTimeApp.servicios.interfaces.ICupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;


@Controller
public class CupoController {

    @Autowired
    private ICupoService cupoService;

    @GetMapping("/cupos-disponibles")
    @ResponseBody
    public List<CupoDTO> obtenerCuposDisponiblesAjax(
            @RequestParam("servicioId") Integer servicioId,
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) {
        Servicio servicio = new Servicio();
        servicio.setId(servicioId);

        return cupoService.obtenerCuposPorServicioYFecha(servicio, fecha)
                .stream()
                .filter(c -> c.getCantidad() > 0)
                .map(c -> new CupoDTO(
                        c.getId(),
                        c.getTurno().getNombreTurno(),
                        c.getTurno().getHoraInicio(),
                        c.getTurno().getHoraFin()
                ))
                .toList();
    }



    @PostMapping("/confirmar-cupo")
    @ResponseBody
    public String confirmarCupo(
            @RequestParam("servicioId") Integer servicioId,
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("turnoId") Integer turnoId
    ) {
        try {
            Servicio servicio = new Servicio();
            servicio.setId(servicioId);

            Turno turno = new Turno();
            turno.setId(turnoId);

            cupoService.ocuparCupo(servicio, fecha, turno);

            return "Cupo confirmado con éxito ";
        } catch (RuntimeException e) {
            return " " + e.getMessage();
        }
    }

}


