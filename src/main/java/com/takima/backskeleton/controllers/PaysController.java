package com.takima.backskeleton.controllers;
import com.takima.backskeleton.models.Pays;
import com.takima.backskeleton.services.PaysService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pays")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // autorisation a toute les adresses pour des raisons de dev
public class PaysController {

    private final PaysService paysService;

    @GetMapping("/")
    public ResponseEntity<List<Pays>> getAll() {
        return ResponseEntity.ok(paysService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pays> getById(@PathVariable Long id) {
        return paysService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/seed")
    public ResponseEntity<Void> seed() {
        paysService.remplirPaysETContinent();
        return ResponseEntity.noContent().build();
    }

}
