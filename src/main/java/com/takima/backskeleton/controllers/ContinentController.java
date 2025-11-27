package com.takima.backskeleton.controllers;

import com.takima.backskeleton.DTO.ContinentDto;
import com.takima.backskeleton.DTO.CountryDto;
import com.takima.backskeleton.models.Country;
import com.takima.backskeleton.models.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.takima.backskeleton.services.ContinentService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/continents")
@RequiredArgsConstructor
public class ContinentController {

    private final ContinentService continentService;

    @GetMapping("/")
    public ResponseEntity<List<ContinentDto>> getAll() {
        List<ContinentDto> continents = continentService.findAll().stream()
                .map(continent -> new ContinentDto(
                        //nom du continent
                        continent.getName(),
                        continent.getCountries().stream()
                                .map(Country::getName)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(continents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContinentDto> getById(@PathVariable Long id) {
        return continentService.findById(id)
                .map(continent -> new ContinentDto(
                        continent.getName(),
                        continent.getCountries().stream()
                                .map(Country::getName)
                                .collect(Collectors.toList())
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{name}/countries")
    public ResponseEntity<List<CountryDto>> getCountriesByContinent(@PathVariable String name) {
        return continentService.findByName(name)
                .map(continent -> {
                    List<CountryDto> countries = continent.getCountries().stream()
                            .map(countryDto -> new CountryDto(
                                    countryDto.getName(),
                                    countryDto.getFlag(),
                                    countryDto.getCapital(),
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
