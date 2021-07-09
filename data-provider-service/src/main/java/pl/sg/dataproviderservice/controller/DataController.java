package pl.sg.dataproviderservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    int count = 0;

    @GetMapping(value = "/test")
    public String test() {
        count++;
        System.out.println("Count: " + count + System.currentTimeMillis());
        return "DATA PROVIDER SERVICE!";
    }

}
