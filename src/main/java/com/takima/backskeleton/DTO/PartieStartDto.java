package com.takima.backskeleton.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PartieStartDto {
    private String date;
    private Integer nbQuestions;
    private List<Long> countryIds;   // on pioche les questions parmi ces pays (ou vide = random)
}
