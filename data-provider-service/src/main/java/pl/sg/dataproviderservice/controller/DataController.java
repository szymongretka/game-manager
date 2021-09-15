package pl.sg.dataproviderservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PreAuthorize("hasAuthority('SCOPE_TEST')")
    @GetMapping("/ping")
    public String ping() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return "Scopes: " + authentication.getAuthorities();
    }

}
