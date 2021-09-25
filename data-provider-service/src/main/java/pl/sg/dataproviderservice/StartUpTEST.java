package pl.sg.dataproviderservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import pl.sg.dataproviderservice.model.Admin;
import pl.sg.dataproviderservice.repository.AdminRepository;

import java.util.Arrays;
import java.util.List;

@Component
public class StartUpTEST {

    @Autowired
    private AdminRepository adminRepository;

    @Bean
    public CommandLineRunner loadData() {
        return (args) -> {
            List<Admin> admins = adminRepository.findAll();
            if (admins.isEmpty()) {
                adminRepository.save(
                        Admin.builder()
                                .username("admin")
                                .password(BCrypt.hashpw("admin", BCrypt.gensalt()))
                                .email("admin@mail.com")
                                .roles(Arrays.asList("ADMIN"))
                                .build());
            }
        };
    }

}
