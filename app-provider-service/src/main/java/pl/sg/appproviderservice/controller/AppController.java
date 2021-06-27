package pl.sg.appproviderservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping(value = "/test")
    public String test() {
        return "App provider service!!!";
    }

}
