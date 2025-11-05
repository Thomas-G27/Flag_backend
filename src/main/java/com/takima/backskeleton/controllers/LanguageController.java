package com.takima.backskeleton.controllers;

import com.takima.backskeleton.DTO.LanguageDto;
import com.takima.backskeleton.models.Language;
import com.takima.backskeleton.services.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/languages")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LanguageController {

    private final LanguageService languageService;

    @GetMapping("/")
    public ResponseEntity<List<LanguageDto>> getAll() {
        List<LanguageDto> languages = languageService.findAll().stream()
                .map(language -> new LanguageDto(
                        language.getName(),
                        language.getIso639_1(),
                        language.getCountries().stream()
                                .map(country -> country.getName())
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(languages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LanguageDto> getById(@PathVariable Long id) {
        return languageService.findById(id)
                .map(language -> new LanguageDto(
                        language.getName(),
                        language.getIso639_1(),
                        language.getCountries().stream()
                                .map(country -> country.getName())
                                .collect(Collectors.toList())
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
