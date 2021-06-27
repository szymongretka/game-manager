package pl.sg.dataproviderservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @GetMapping(value = "/test")
    public String test() {
        return "DATA PROVIDER SERVICE!";
    }

}
