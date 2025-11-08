package com.takima.backskeleton.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GameDto {
    private Long id;
    private Float score;
    private Timestamp date;
    private String categorie;
    private String utilisateur_name;
    
}
