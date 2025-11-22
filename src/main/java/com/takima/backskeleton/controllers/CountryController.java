package com.takima.backskeleton.controllers;

import com.takima.backskeleton.DTO.*;
import com.takima.backskeleton.models.Language;
import com.takima.backskeleton.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping("/")
    public ResponseEntity<List<CountryDto>> getAll() {
        List<CountryDto> countries = countryService.findAll().stream()
                .map(country -> new CountryDto(
                        country.getName(),
                        country.getFlag(),
                        country.getCapital(),
                        country.getContinent().getName(),
                        country.getLanguages().stream()
                                .map(Language::getName)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(countries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDto> getById(@PathVariable Long id) {
        return countryService.findById(id)
                .map(country -> new CountryDto(
                        country.getName(),
                        country.getFlag(),
                        country.getCapital(),
                        country.getContinent().getName(),
                        country.getLanguages().stream()
                                .map(Language::getName)
                                .collect(Collectors.toList())
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<CountryDto> getByCode(@PathVariable String code) {
        return countryService.findByCode(code)
                .map(country -> new CountryDto(
                        country.getName(),
                        country.getFlag(),
                        country.getCapital(),
                        country.getContinent().getName(),
                        country.getLanguages().stream()
                                .map(Language::getName)
                                .collect(Collectors.toList())
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/capital/{capital_name}")
    public ResponseEntity<CountryDto> getByCapital(@PathVariable String capital_name) {
        return countryService.findByCapital(capital_name)
                .map(country -> new CountryDto(
                        country.getName(),
                        country.getFlag(),
                        country.getCapital(),
                        country.getContinent().getName(),
                        country.getLanguages().stream()
                                .map(Language::getName)
                                .collect(Collectors.toList())
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/with-capital")
    public ResponseEntity<List<CountryDto>> getWithCapital() {
        List<CountryDto> countries = countryService.findAllWithCapital().stream()
                .map(country -> new CountryDto(
                        country.getName(),
                        country.getFlag(),
                        country.getCapital(),
                        country.getContinent().getName(),
                        country.getLanguages().stream()
                                .map(Language::getName)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(countries);
    }
}
