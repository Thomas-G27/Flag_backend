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
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.List;

@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "url", nullable = false, unique = true)
    private String url;
    @ManyToMany
    @JoinTable(
            name = "country_language",
            joinColumns = @JoinColumn(name = "country_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    private List<Language> languages;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "continent_id")
    private Continent continent;

        
    private Country(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.url = builder.url;
        this.languages = builder.languages;
    }

    
    public Country() {
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getUrl() {
        return url;
    }
    
    
    public List<Language> getLanguages() {
        return languages;
    }
    private static class Builder {
        private Long id;
        private String name;
        private String url;
        private String continent;
        private List<Language> languages;

        public Builder id (Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        

        public Builder languages(List<Language> languages) {
            this.languages = languages;
            return this;
        }

        public Country build() {
            return new Country(this);
        }
    }
}

