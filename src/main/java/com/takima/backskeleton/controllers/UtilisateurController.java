package com.takima.backskeleton.controllers;


import com.takima.backskeleton.DTO.UtilisateurCreateDto;
import com.takima.backskeleton.DTO.UtilisateurDto;
import com.takima.backskeleton.models.Utilisateur;
import com.takima.backskeleton.services.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
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

    @PostMapping("/")
    public ResponseEntity<String> add(@RequestBody UtilisateurCreateDto utilisateurCreateDto){
        try {
            utilisateurService.addUtilisateur(utilisateurCreateDto);
            return ResponseEntity.ok("Utilisateur ajouté !");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
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
