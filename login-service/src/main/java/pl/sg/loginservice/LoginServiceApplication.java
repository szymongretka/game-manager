package pl.sg.loginservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import pl.sg.loginservice.security.jwt.JwtProperties;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties({JwtProperties.class})
public class LoginServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginServiceApplication.class, args);
    }

}
