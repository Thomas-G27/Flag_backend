package com.takima.backskeleton.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private Long id;
    private Integer ordre;
    private String enonce;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
}
