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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                        game.getId(),
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
                        game.getId(),
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
                        game.getId(),
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
                        game.getId(),
                        game.getScore(),
                        game.getGame_date(),
                        game.getCategorie(),
                        game.getUtilisateur().getName()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(games);
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> add(@RequestBody GameCreateDto gameCreateDto) {
        try {
            gameService.addGame(gameCreateDto);

            Map<String, Object> response = new HashMap<>();
            response.put("response", 200);
            response.put("message", "Partie sauvegardé !");

            return ResponseEntity.ok(response);
        } catch (Exception e) {

            Map<String, Object> error_response = new HashMap<>();
            error_response.put("response", 400);
            error_response.put("message", "probleme lors de l'ajout en bdd");

            return ResponseEntity.badRequest().body(error_response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteById(@PathVariable Long id) {
        try {
            gameService.deleteGameById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("response", 200);
            response.put("message", "Partie supprimée !");

            return ResponseEntity.ok(response);
        } catch (Exception e) {

            Map<String, Object> error_response = new HashMap<>();
            error_response.put("response", 400);
            error_response.put("message", "probleme lors de la suppression...");

            return ResponseEntity.badRequest().body(error_response);
        }
    }
}
