package com.takima.backskeleton.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LanguageDto {
    private Long languageId;
    private String name;
    private Set<PaysDto> pays=new HashSet<>();
}
