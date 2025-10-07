package com.takima.backskeleton.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "languages")
    @JsonIgnore
    private List<Country> countries;

    public Language() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Country> getCountries() {
        return countries;
    }
    
}
