package org.esfe.BeautyTimeApp.controladores;

import org.esfe.BeautyTimeApp.modelos.Categoria;
import org.esfe.BeautyTimeApp.modelos.Servicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/servicios") // ← RUTA BASE CAMBIADA
public class UsCategoriaController {

    @GetMapping("/categoria/{id}") // ← NUEVA RUTA: /servicios/categoria/{id}
    public String serviciosPorCategoria(@PathVariable Integer id, Model model) {
        model.addAttribute("servicios", getServiciosMock(id));
        model.addAttribute("categoriaId", id);
        return "home/categoria";
    }

    // Mock de servicios - 3 SERVICIOS POR CATEGORÍA
    private List<Servicio> getServiciosMock(Integer categoriaId) {
        if (categoriaId == 1) { // Uñas - 3 servicios
            return Arrays.asList(
                    new Servicio("Manicura básica", "Limpieza, corte y esmaltado básico", new BigDecimal("25.00"), 30, new Categoria(1, "Uñas")),
                    new Servicio("Uñas acrílicas", "Extensiones y diseño profesional", new BigDecimal("45.00"), 60, new Categoria(1, "Uñas")),
                    new Servicio("Pedicura spa", "Tratamiento completo con hidratación", new BigDecimal("35.00"), 45, new Categoria(1, "Uñas"))
            );
        } else if (categoriaId == 2) { // Cabello - 3 servicios
            return Arrays.asList(
                    new Servicio("Corte de cabello", "Corte moderno y personalizado", new BigDecimal("30.00"), 40, new Categoria(2, "Cabello")),
                    new Servicio("Coloración", "Tinte profesional y mechas", new BigDecimal("60.00"), 90, new Categoria(2, "Cabello")),
                    new Servicio("Tratamiento capilar", "Hidratación y reconstrucción", new BigDecimal("40.00"), 50, new Categoria(2, "Cabello"))
            );
        } else if (categoriaId == 3) { // Maquillaje - 3 servicios
            return Arrays.asList(
                    new Servicio("Maquillaje social", "Look natural para día a día", new BigDecimal("35.00"), 45, new Categoria(3, "Maquillaje")),
                    new Servicio("Maquillaje de novia", "Duradero y fotográfico", new BigDecimal("80.00"), 90, new Categoria(3, "Maquillaje")),
                    new Servicio("Maquillaje artístico", "Diseños creativos y especiales", new BigDecimal("50.00"), 60, new Categoria(3, "Maquillaje"))
            );
        } else if (categoriaId == 4) { // Skincare - 3 servicios
            return Arrays.asList(
                    new Servicio("Limpieza facial", "Limpieza profunda y exfoliación", new BigDecimal("40.00"), 50, new Categoria(4, "Skincare")),
                    new Servicio("Hidratación facial", "Tratamiento intensivo de hidratación", new BigDecimal("45.00"), 55, new Categoria(4, "Skincare")),
                    new Servicio("Anti-arrugas", "Tratamiento rejuvenecedor", new BigDecimal("60.00"), 70, new Categoria(4, "Skincare"))
            );
        } else if (categoriaId == 5) { // Depilación - 3 servicios
            return Arrays.asList(
                    new Servicio("Depilación cera", "Depilación completa con cera", new BigDecimal("25.00"), 30, new Categoria(5, "Depilación")),
                    new Servicio("Depilación facial", "Cejas, bigote y patillas", new BigDecimal("15.00"), 20, new Categoria(5, "Depilación")),
                    new Servicio("Depilación corporal", "Brazos, piernas y espalda", new BigDecimal("35.00"), 40, new Categoria(5, "Depilación"))
            );
        }
        return Arrays.asList();
    }
}