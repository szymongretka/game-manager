package pl.sg.apigateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sg.apigateway.dto.AdminResponseDTO;

import java.util.List;

import static pl.sg.apigateway.constant.MicroserviceConstants.BASE_API;
import static pl.sg.apigateway.constant.MicroserviceConstants.DATA_PROVIDER_MICROSERVICE;

@FeignClient(name = DATA_PROVIDER_MICROSERVICE)
@Service
public interface AdminInterface {

    @RequestMapping(value = "/admins")
    List<AdminResponseDTO> fetchAdmins();
}