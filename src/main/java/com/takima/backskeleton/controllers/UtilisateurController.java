package com.takima.backskeleton.controllers;

import com.takima.backskeleton.models.Utilisateur;
import com.takima.backskeleton.services.UtilisateurService;
import com.takima.backskeleton.services.UtilisateurService.UtilisateurDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
@CrossOrigin
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @PostMapping
    public ResponseEntity<Utilisateur> create(@RequestBody UtilisateurDto dto) {
        Utilisateur saved = utilisateurService.create(dto);
        return ResponseEntity
                .created(URI.create("/api/utilisateurs/" + saved.getId()))
                .body(saved);
    }
    //Liste des utilisateurs
    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAll() {
        return ResponseEntity.ok(utilisateurService.findAll());
    }

    // Récupérer un utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getById(@PathVariable Long id) {
        return ResponseEntity.ok(utilisateurService.findById(id));
    }

    // Mettre à jour un utilisateur
    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> update(@PathVariable Long id, @RequestBody Utilisateur user) {
        return ResponseEntity.ok(utilisateurService.update(id, user));
    }

    // Supprimer un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        utilisateurService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
