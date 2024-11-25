package com.pes.pedido_ms.configs.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenValidator jwtTokenValidator;
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenValidator jwtTokenValidator, JwtUtil jwtUtil) {
        this.jwtTokenValidator = jwtTokenValidator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromRequest(request);

        if (token != null) {
            try {
                // Valida o token JWT
                DecodedJWT decodedJWT = jwtTokenValidator.validateToken(token);

                // Extraindo informações do token
                String userId = decodedJWT.getSubject();
                String email = decodedJWT.getClaim("email").asString();
                String role = decodedJWT.getClaim("role").asString();

                // Configura o JwtUtil com os detalhes
                jwtUtil.setJwtDetails(token, userId, email, role);

                // Marca o usuário como autenticado no contexto de segurança
                JwtAuthenticatedUser authenticatedUser = new JwtAuthenticatedUser(email, role);
                SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

            } catch (Exception e) {
                // Token inválido ou erro na validação
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response); // Continua o fluxo
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove "Bearer " do token
        }
        return null;
    }
}

