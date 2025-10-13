package com.takima.backskeleton.DTO;

import com.takima.backskeleton.models.Pays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaysDto {
    private Long id;
    private String name;
    private String drapeau;
    private Set<LanguageDto> languageDtos;
}
