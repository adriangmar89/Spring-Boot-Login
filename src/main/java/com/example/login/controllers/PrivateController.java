package com.example.login.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PrivateController {

    @GetMapping("/test")
    public String test() {
        return "¡Esta es una ruta privada! Solo accesible con un token válido.";
    }

    // Ruta accesible solo para usuarios con rol 'admin'
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/testAdmin")
    public String testAdmin() {
        return "¡Esta es una ruta protegida para administradores!";
    }

}
