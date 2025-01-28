package br.com.farmacia.farmacia.service;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
    public static Boolean validateLogin(String usuario, String senha) {
        return "admin".equals(usuario) && "1234".equals(senha);
    }
}

