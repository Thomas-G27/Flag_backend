package com.takima.backskeleton.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UtilisateurDto {
    private Long id;
    private String name;
    private Long email;
}
