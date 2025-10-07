package com.takima.backskeleton.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "parties")
public class Partie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "score", nullable = false)
    private Integer score;
    @Column(name = "date", nullable = false)
    private Instant date;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

   private Partie(Builder builder) {
        this.id = builder.id;
        this.score = builder.score;
        this.date = builder.date;
        this.user = builder.user;
    }
    public Partie() {
    }

    public Long getId() {
        return id;
    }

    public Integer getScore() {
        return score;
    }

    public Instant getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public static class Builder {
        private Long id;
        private Integer score;
        private Instant date;
        private User user;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setScore(Integer score) {
            this.score = score;
            return this;
        }

        public Builder setDate(Instant date) {
            this.date = date;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Partie build() {
            return new Partie(this);
        }
    }

}
