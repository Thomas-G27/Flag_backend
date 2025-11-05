package com.takima.backskeleton.DTO;

import com.takima.backskeleton.models.Country;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContinentDto {
    private String name;
    private List<String> countries_names;
}
