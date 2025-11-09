package com.takima.backskeleton.controllers;


import com.takima.backskeleton.DTO.UtilisateurCreateDto;
import com.takima.backskeleton.DTO.UtilisateurDto;
import com.takima.backskeleton.models.Utilisateur;
import com.takima.backskeleton.services.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @GetMapping("/")
    public ResponseEntity<List<UtilisateurDto>> getAll(){
        List<UtilisateurDto> utilisateurs = utilisateurService.findAll().stream()
                .map(utilisateur -> new UtilisateurDto(
                        utilisateur.getName(),
                        utilisateur.getEmail()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/{name}")
    public ResponseEntity<UtilisateurDto> getbyName(@PathVariable String name){
        return utilisateurService.findByName(name)
                .map(utilisateur -> new UtilisateurDto(
                        utilisateur.getName(),
                        utilisateur.getEmail()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Destinée a être supprimé car remplacée par le register de AuthController
    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> add(@RequestBody UtilisateurCreateDto utilisateurCreateDto){
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

    @DeleteMapping("/{name}")
    public ResponseEntity<String> delete(@PathVariable String name){
        try {
            // on récupère l'utilisateur
            Optional<Utilisateur> optionalUtilisateur = utilisateurService.findByName(name);
            if (optionalUtilisateur.isEmpty()) {
                return ResponseEntity.notFound().build(); // 404 si pas trouvé
            }
            utilisateurService.deleteUtilisateur(optionalUtilisateur.get());
            return ResponseEntity.ok("utilisateur supprimé !");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }
}
