package com.takima.backskeleton.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Partie")
public class Partie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String score;
    private String date;
    @ManyToMany
    @JoinTable(
            name = "partie_pays",
            joinColumns = @JoinColumn(name = "partie_id"),
            inverseJoinColumns = @JoinColumn(name = "pays_id")
    )
    private Set<Pays> pays = new HashSet<>();
    @OneToMany(mappedBy = "partie", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ordre ASC")
    private List<Question> questions = new ArrayList<>();
}
