package dh.projetointegradorctd.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ViewController {

    @GetMapping(value = {"/"}, produces = "text/html")
    public String getting () {
        return "index";
    }

    @GetMapping(value = {"/error"}, produces = "text/html")
    public String errorHandler (HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status.equals(HttpStatus.NOT_FOUND.value())) {
            return "index";
        }
        return "error";
    }
}
