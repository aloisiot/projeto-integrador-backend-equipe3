package dh.projetointegradorctd.backend.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController implements ErrorController {
    @GetMapping(value = {"/", "/error"})
    public String getting() {
        return "index";
    }
}
