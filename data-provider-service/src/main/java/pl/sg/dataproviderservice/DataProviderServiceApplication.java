package pl.sg.dataproviderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DataProviderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataProviderServiceApplication.class, args);
    }

}
