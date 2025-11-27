package com.takima.backskeleton.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class CountryDto {
    private String name;
    private String flag;
    private String capital;
    private String continent;
    private List<String> languages;
}
