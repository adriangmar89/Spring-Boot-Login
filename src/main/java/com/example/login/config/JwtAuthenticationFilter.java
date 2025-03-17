package com.example.login.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String SECRET_KEY;

    public JwtAuthenticationFilter(String secretKey) {
        this.SECRET_KEY = secretKey;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain ) throws ServletException, IOException {
        // Obtener el token del encabezado Authorization
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Continuar con el siguiente filtro
            return;
        }
        String token = header.replace("Bearer ", "");
        try {
            // Validar el token
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            // Obtener el email y el rol del token
            String email = claims.getSubject();
            String role = claims.get("role", String.class);
            // Crear una autenticación para Spring Security
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    List.of(new SimpleGrantedAuthority(role))
            );
            // Establecer la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            // Si el token no es válido, limpiar el contexto de seguridad
            SecurityContextHolder.clearContext();
        }
        // Continuar con el siguiente filtro
        filterChain.doFilter(request, response);
    }

}
