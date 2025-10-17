package com.takima.backskeleton.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionResultDto {
    private Long id;
    private Integer ordre;
    private String enonce;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String userOption;    // "A"/"B"/...
    private Boolean isCorrect;
}