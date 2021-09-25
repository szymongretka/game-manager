package pl.sg.loginservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sg.loginservice.dto.AdminResponseDTO;

import java.util.List;

@FeignClient("data")
@Service
public interface AdminInterface {

    @RequestMapping(value = "/admins")
    List<AdminResponseDTO> fetchAdmins();
}