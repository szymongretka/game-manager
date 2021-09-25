package pl.sg.loginservice.security.service;

import pl.sg.loginservice.dto.LoginRequestDTO;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    String login(LoginRequestDTO requestDTO, HttpServletRequest request);
}
