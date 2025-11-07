package com.takima.backskeleton.controllers;


import com.takima.backskeleton.DTO.GameCreateDto;
import com.takima.backskeleton.DTO.GameDto;
import com.takima.backskeleton.DTO.UtilisateurDto;
import com.takima.backskeleton.models.Game;
import com.takima.backskeleton.models.Utilisateur;
import com.takima.backskeleton.services.GameService;
import com.takima.backskeleton.services.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/games")
@AllArgsConstructor
@CrossOrigin("*")
public class GameController {

    private final GameService gameService;
    private final UtilisateurService utilisateurService;

    @GetMapping("/")
    public ResponseEntity<List<GameDto>> getAll() {
        List<GameDto> games = gameService.findAll().stream()
                .map(game -> new GameDto(
                        game.getScore(),
                        game.getGame_date(),
                        game.getCategorie(),
                        game.getUtilisateur().getName()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(games);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDto> getById(@PathVariable Long id) {
        return gameService.findById(id)
                .map(game -> new GameDto(
                        game.getScore(),
                        game.getGame_date(),
                        game.getCategorie(),
                        game.getUtilisateur().getName()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/utilisateur/{utilisateur_name}")
    public ResponseEntity<List<GameDto>> getByUtilisateurName(@PathVariable String utilisateur_name) {
        Optional<Utilisateur> optionalUtilisateur = utilisateurService.findByName(utilisateur_name);
        if (optionalUtilisateur.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404 si utilisateur pas trouvé
        }
        List<GameDto> games = gameService.findByUtilisateur(optionalUtilisateur.get()).stream()
                .map(game -> new GameDto(
                        game.getScore(),
                        game.getGame_date(),
                        game.getCategorie(),
                        game.getUtilisateur().getName()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(games);
    }

    @GetMapping("/categorie/{categorie}")
    public ResponseEntity<List<GameDto>> getByCategorie(@PathVariable String categorie){
        List<GameDto> games = gameService.findByCategorie(categorie).stream()
                .map(game -> new GameDto(
                        game.getScore(),
                        game.getGame_date(),
                        game.getCategorie(),
                        game.getUtilisateur().getName()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(games);
    }

    @PostMapping("/")
    public ResponseEntity<String> add(@RequestBody GameCreateDto gameCreateDto) {
        try {
            gameService.addGame(gameCreateDto);
            return ResponseEntity.ok("Game ajoutée !");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        try {
            gameService.deleteGameById(id);
            return ResponseEntity.ok("Game supprimée !");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }
}
