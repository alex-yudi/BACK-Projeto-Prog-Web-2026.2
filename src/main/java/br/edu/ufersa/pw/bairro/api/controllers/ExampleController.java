package br.edu.ufersa.pw.bairro.api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {
    @GetMapping("/example")
    public String example(){
        return "Exemplo";
    }
}
