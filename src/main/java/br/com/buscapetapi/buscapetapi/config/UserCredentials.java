package br.com.buscapetapi.buscapetapi.config;

import jakarta.servlet.http.HttpServletRequest;

public class UserCredentials {

    public static Long getUserId(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        // Extraído do token JWT
        if (userId == null) {
            throw new RuntimeException("Usuario não encontrado");
        }
        return userId;
    }
}
