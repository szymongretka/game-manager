package pl.sg.loginservice.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sg.loginservice.constants.PatternConstants;
import pl.sg.loginservice.dto.AdminResponseDTO;
import pl.sg.loginservice.dto.LoginRequestDTO;
import pl.sg.loginservice.exception.UnauthorisedException;
import pl.sg.loginservice.feign.AdminInterface;
import pl.sg.loginservice.security.jwt.JwtTokenProvider;
import pl.sg.loginservice.security.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sg.loginservice.util.DateUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

    private final JwtTokenProvider jwtTokenProvider;
    private final AdminInterface adminInterface;

    @Override
    public String login(LoginRequestDTO loginRequestDTO, HttpServletRequest request) {
        LOGGER.info("LOGIN PROCESS STARTED ::::");

        long startTime = DateUtils.getTimeInMillisecondsFromLocalDate();

        AdminResponseDTO admin = fetchAdminDetails(loginRequestDTO);

        validateAdminUsername(admin);

        //validateAdminStatus(admin);

        validatePassword(loginRequestDTO, admin);

        String jwtToken = jwtTokenProvider.createToken(loginRequestDTO.getUsername(), request);

        LOGGER.info("LOGIN PROCESS COMPLETED IN ::: " + (DateUtils.getTimeInMillisecondsFromLocalDate() - startTime)
                + " ms");

        return jwtToken;
    }

    private AdminResponseDTO fetchAdminDetails(LoginRequestDTO loginRequestDTO) {
        //Pattern pattern = Pattern.compile(PatternConstants.EmailConstants.EMAIL_PATTERN);
        //Matcher m = pattern.matcher(loginRequestDTO.getUsername());

        return adminInterface.fetchAdmins().get(0);

//        return m.find() ? adminInterface.searchAdmin
//                (AdminRequestDTO.builder().username(null).emailAddress(loginRequestDTO.getUserCredential()).build())
//                : adminInterface.searchAdmin
//                (AdminRequestDTO.builder().username(loginRequestDTO.getUserCredential()).emailAddress(null).build());
    }

    private void validateAdminUsername(AdminResponseDTO admin) {
        if (Objects.isNull(admin))
            throw new UnauthorisedException("Admin with given username doesn't exits.", "Admin entity returned null");
        LOGGER.info(":::: ADMIN USERNAME VALIDATED ::::");
    }

    private void validateAdminStatus(AdminResponseDTO admin) {

    }

    private void validatePassword(LoginRequestDTO loginRequestDTO, AdminResponseDTO adminResponseDTO) {

        LOGGER.info(":::: ADMIN PASSWORD VALIDATION ::::");

        if (BCrypt.checkpw(loginRequestDTO.getPassword(), adminResponseDTO.getPassword())) {
            adminResponseDTO.setLoginAttempt(0);
            //adminInterface.updateAdmin(admin);
        } else {
            adminResponseDTO.setLoginAttempt(adminResponseDTO.getLoginAttempt() + 1);

            if (adminResponseDTO.getLoginAttempt() >= 3) {
                //adminResponseDTO.setStatus('B');
                //adminInterface.updateAdmin(admin);

                LOGGER.debug("ADMIN IS BLOCKED DUE TO MULTIPLE WRONG ATTEMPTS...");
//                throw new UnauthorisedException(IncorrectPasswordAttempts.MESSAGE,
//                        IncorrectPasswordAttempts.DEVELOPER_MESSAGE);
            }

            LOGGER.debug("INCORRECT PASSWORD...");
            throw new UnauthorisedException("Password didn't match with the original one.", "Incorrect password.Forgot Password?");
        }

        LOGGER.info(":::: ADMIN PASSWORD VALIDATED ::::");
    };


}
