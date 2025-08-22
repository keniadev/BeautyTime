package org.esfe.BeautyTimeApp.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")//que sea la primera en mostrarse
public class HomeController {
    @GetMapping
    public String index() {
        return "home/index";
    }
}