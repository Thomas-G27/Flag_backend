package com.takima.backskeleton.controllers;

import com.takima.backskeleton.DTO.*;
import com.takima.backskeleton.services.PartieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parties")
@RequiredArgsConstructor
@CrossOrigin
public class PartieController {

    private final PartieService partieService;

    /** Démarrer une partie avec N questions (pays choisis ou random) */
    @PostMapping("/start")
    public ResponseEntity<PartieSummaryDto> start(@RequestBody PartieStartDto dto) {
        PartieSummaryDto started = partieService.startPartie(dto);
        return ResponseEntity.status(201).body(started);
    }

    /** Récupérer la prochaine question non répondue */
    @GetMapping("/{id}/next-question")
    public ResponseEntity<QuestionDto> next(@PathVariable Long id) {
        QuestionDto q = partieService.nextQuestion(id);
        return ResponseEntity.ok(q); // peut être null si terminé
    }

    /** Soumettre une réponse */
    @PostMapping("/{id}/questions/{qid}/answer")
    public ResponseEntity<QuestionResultDto> answer(@PathVariable Long id,
                                                    @PathVariable Long qid,
                                                    @RequestBody ReponseDto answer) {
        return ResponseEntity.ok(partieService.answer(id, qid, answer));
    }

    /** Récapitulatif / score */
    @GetMapping("/{id}")
    public ResponseEntity<PartieSummaryDto> summary(@PathVariable Long id) {
        return ResponseEntity.ok(partieService.getSummary(id));
    }
}
