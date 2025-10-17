package com.takima.backskeleton.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "question")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partie_id")
    private Partie partie;

    private Integer ordre;
    private String enonce;

    // propositions
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    @Enumerated(EnumType.STRING)
    private Option correctOption;

    @Enumerated(EnumType.STRING)
    private Option userOption;

    private Boolean isCorrect;

    // rattacher la question à un pays "cible"
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pays_id")
    private Pays pays;

    public enum Option { A, B, C, D }
}
