package com.takima.backskeleton.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PartieSummaryDto {
    private Long id;
    private String score;
    private List<QuestionResultDto> questions;
}