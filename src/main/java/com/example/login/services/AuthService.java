package com.example.login.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.login.models.User;
import com.example.login.models.AuthResponse;
import com.example.login.repositories.UserRepository;
import java.util.Date;
import javax.crypto.SecretKey;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    // Método para registrar un nuevo usuario y generar un token JWT
    public AuthResponse register(User user) {
        // Validar que el nombre de usuario no esté registrado
        if (userRepository.findByUserName(user.getUserName()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está registrado");
        }
        // Validar que el email no esté registrado
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }
        // Si el rol no se proporciona, asignar "USER" por defecto
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("user");
        }
        // Codificar la contraseña antes de guardarla
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User registeredUser = userRepository.save(user);
        // Generar un token JWT
        String token = generateToken(registeredUser);
        return new AuthResponse(registeredUser, token);
    }

    // Método para autenticar un usuario y generar un token JWT
    public AuthResponse login(String email, String password) {
        // Buscar al usuario por su email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        // Verificar la contraseña
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        // Generar un token JWT
        String token = generateToken(user);
        // Devolver la respuesta con el token y la información del usuario
        return new AuthResponse(user, token);
    }

    // Método para generar un token JWT
    private String generateToken(User user) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        return Jwts.builder()
                .subject(user.getEmail()) // Usar el email como subject
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día de validez
                .signWith(key)
                .compact();
    }

}
