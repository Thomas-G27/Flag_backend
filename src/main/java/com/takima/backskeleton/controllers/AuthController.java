package com.takima.backskeleton.controllers;


import com.takima.backskeleton.DAO.UtilisateurDao;
import com.takima.backskeleton.models.Utilisateur;
import com.takima.backskeleton.security.JwtService;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UtilisateurDao utilisateurDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, UtilisateurDao utilisateurDao,
                          PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.utilisateurDao = utilisateurDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.get("name"), request.get("password"))
        );

        String token = jwtService.generateToken(request.get("name"));
        return Map.of("token", token);
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Utilisateur utilisateur) {
        utilisateur.setMdp(passwordEncoder.encode(utilisateur.getMdp()));
        utilisateurDao.save(utilisateur);
        return Map.of("message", "Utilisateur créé avec succès !");
    }
}
