package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.model.auth.User;
import dh.projetointegradorctd.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users", produces = "application/json;charset=UTF-8")
public class UserController extends TemplateCrudController < User > {

    @Autowired
    public UserController(UserService service) {
        super(service);
    }
}
