package pl.sg.dataproviderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sg.dataproviderservice.model.Admin;
import pl.sg.dataproviderservice.repository.AdminRepository;

import java.util.List;

@RestController
public class DataController {

    int count = 0;

    @Autowired
    AdminRepository adminRepository;

    @GetMapping(value = "/test")
    public String test() {
        count++;
        System.out.println("Count: " + count + System.currentTimeMillis());
        return "DATA PROVIDER SERVICE!";
    }

    @GetMapping(value = "/admins")
    public List<Admin> adminTest() {
        List<Admin> admins = adminRepository.findAll();
        System.out.println("Count of admins: " + admins.size());
        return admins;
    }

}
