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
@Table(name= "continents")
public class Continent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(mappedBy = "continent")
    private List<Country> countries;
    public Continent() {
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
    private Continent(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.countries = builder.countries;
    }
    public static class Builder {
        private Long id;
        private String name;
        private List<Country> countries;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder countries(List<Country> countries) {
            this.countries = countries;
            return this;
        }

        public Continent build() {
            return new Continent(this);
        }
    }
    

    

    
}
