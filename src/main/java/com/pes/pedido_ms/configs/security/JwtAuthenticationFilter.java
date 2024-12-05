package com.pes.pedido_ms.configs.security;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenValidator jwtTokenValidator;
    private final JwtUtil jwtUtil;
    private final Set<String> validRoles = Set.of("superadmin", "adminAbrigo", "adminCD", "voluntario");
    private final Set<String> validRolesPut = Set.of("superadmin", "adminAbrigo", "voluntario");
    private final Set<String> validRolesPost = Set.of("superadmin", "adminAbrigo");

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
                System.out.println("role: "+role);
                if (validRoles.contains(role)) {
                    System.out.println("role valido");
                } else {
                    System.out.println("role invalido");
                    throw new ResponseStatusException(FORBIDDEN);
                }
                // Restrição de acesso baseada no método HTTP e endpoint 
                String requestURI = request.getRequestURI(); String method = request.getMethod();
                
                if (requestURI.equals("/v1/pedidos") && (method.equals("PUT"))) {
                    if (!validRolesPut.contains(role)) {
                        throw new ResponseStatusException(FORBIDDEN,"Access denied: role not allowed");
                    }
                }
                if (requestURI.equals("/v1/pedidos") && (method.equals("POST"))) {
                    if (!validRolesPost.contains(role)) {
                        throw new ResponseStatusException(FORBIDDEN,"Access denied: role not allowed");
                    }
                }

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

