package com.takima.backskeleton.controllers;


import com.takima.backskeleton.DTO.GameDto;
import com.takima.backskeleton.models.Game;
import com.takima.backskeleton.services.GameService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/games")
@AllArgsConstructor
@CrossOrigin("*")
public class GameController {

    private final GameService gameService;

    @GetMapping("/")
    public ResponseEntity<List<GameDto>> getAll() {
        List<GameDto> games = gameService.findAll().stream()
                .map(game -> new GameDto(
                        game.getScore(),
                        game.getDate(),
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
                        game.getDate(),
                        game.getCategorie(),
                        game.getUtilisateur().getName()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
