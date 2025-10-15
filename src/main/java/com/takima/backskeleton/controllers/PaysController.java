package com.takima.backskeleton.controllers;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takima.backskeleton.models.Pays;
import com.takima.backskeleton.services.PaysService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/pays")
@RequiredArgsConstructor
@CrossOrigin
public class PaysController {

    private final PaysService paysService;

    @GetMapping("")
    public ResponseEntity<List<Pays>> getAll() {
        return ResponseEntity.ok(paysService.findAll());
    }

    @GetMapping("/first")
    public ResponseEntity<Pays> getFirst() {
        return paysService.findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/seed")
    public ResponseEntity<Void> seed() {
        paysService.remplirPaysETContinent();
        return ResponseEntity.noContent().build();
    }

}
