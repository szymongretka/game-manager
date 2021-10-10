package pl.sg.dataproviderservice.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    int count = 0;

    @GetMapping(value = "/test")
    public String test(@AuthenticationPrincipal JwtAuthenticationToken jwtToken) {
        System.out.println("Browsed by " + jwtToken.getTokenAttributes().get("given_name"));
        count++;
        System.out.println("Count: " + count + System.currentTimeMillis());
        return "DATA PROVIDER SERVICE!";
    }

}
