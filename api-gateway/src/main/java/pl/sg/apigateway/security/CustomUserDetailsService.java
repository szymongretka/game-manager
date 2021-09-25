package pl.sg.apigateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.sg.apigateway.dto.AdminResponseDTO;
import pl.sg.apigateway.feign.AdminInterface;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminInterface adminInterface;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminResponseDTO adminResponseDTO = adminInterface.fetchAdmins().get(0); //TODO

        List<GrantedAuthority> grantedAuthorities = adminResponseDTO.getRoles()
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new User(String.join("-", username, adminResponseDTO.getEmailAddress()),
                adminResponseDTO.getPassword(), true, true, true,
                true, grantedAuthorities);
    }
}
