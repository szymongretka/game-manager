package pl.sg.apigateway.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
//@PropertySource("classpath:security.yml")
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secretKey;
}