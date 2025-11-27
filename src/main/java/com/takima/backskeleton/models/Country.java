package com.takima.backskeleton.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "country")
@Getter
@Setter
@NoArgsConstructor
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String flag;
    private String capital;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "continent_id")
    @JsonBackReference
    private Continent continent;

    @ManyToMany
    @JoinTable(
            name = "country_language",
            joinColumns = @JoinColumn(name = "country_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private Set<Language> languages = new HashSet<>();
}
