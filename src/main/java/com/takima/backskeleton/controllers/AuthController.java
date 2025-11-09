package com.takima.backskeleton.controllers;


import com.takima.backskeleton.DAO.UtilisateurDao;
import com.takima.backskeleton.DTO.UtilisateurCreateDto;
import com.takima.backskeleton.models.Utilisateur;
import com.takima.backskeleton.security.JwtService;
import com.takima.backskeleton.services.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UtilisateurDao utilisateurDao;
    private final UtilisateurService utilisateurService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.get("name"), request.get("password"))
        );
        // Récupération de l'utilisateur pour recupérer le parametre isadmin
        Utilisateur utilisateur = utilisateurDao.findByName(request.get("name"))
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String token = jwtService.generateToken(utilisateur.getName(), utilisateur.is_admin());
        return Map.of("token", token);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UtilisateurCreateDto utilisateurCreateDto) {
        try {
            Optional<Utilisateur> user_exist = utilisateurService.findByName(utilisateurCreateDto.getName());

            if (user_exist.isPresent()) {
                Map<String, Object> error_response = new HashMap<>();
                error_response.put("response", 400);
                error_response.put("message", "user already exists");
                return ResponseEntity.badRequest().body(error_response);
            }
            else {
                utilisateurService.addUtilisateur(utilisateurCreateDto);

                Map<String, Object> response = new HashMap<>();
                response.put("response", 200);
                response.put("message", "Utilisateur ajouté !");

                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {

            Map<String, Object> error_response = new HashMap<>();
            error_response.put("response", 400);
            error_response.put("message", "probleme lors de l'ajout en bdd");

            return ResponseEntity.badRequest().body(error_response);
        }
    }
}
