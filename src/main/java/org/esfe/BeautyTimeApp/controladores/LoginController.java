package org.esfe.BeautyTimeApp.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String mostrarLogin() {
        return "/usuario/login"; // busca src/main/resources/templates/login.html
    }
}
