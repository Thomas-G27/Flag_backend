package com.takima.backskeleton.controllers;

import com.takima.backskeleton.DTO.CountryDto;
import com.takima.backskeleton.DTO.LanguageDto;
import com.takima.backskeleton.models.Country;
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
                                .map(Country::getName)
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
                                .map(Country::getName)
                                .collect(Collectors.toList())
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{name}/countries")
    public ResponseEntity<List<CountryDto>> getCountriesByLanguage(@PathVariable String name) {
        return languageService.findByName(name)
                .map(language -> {
                    List<CountryDto> countries = language.getCountries().stream()
                            .map(countryDto -> new CountryDto(
                                    countryDto.getName(),
                                    countryDto.getFlag(),
                                    countryDto.getContinent().getName(),
                                    countryDto.getLanguages().stream()
                                            .map(Language::getName)
                                            .collect(Collectors.toList())
                            ))
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(countries);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
