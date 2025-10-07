package com.takima.backskeleton.DAO;

import org.springframework.stereotype.Repository;
import com.takima.backskeleton.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

@Repository

public interface CountryDao extends JpaRepository<Country, Long> {
    @Query("SELECT c FROM Country c JOIN c.languages l WHERE l.id = :languageId")
    List<Country> findByLanguage(int languageId);
    @Query("SELECT c FROM Country c JOIN c.continents co WHERE co.id= :continentId")
    List<Country> findByContinent(int contienentId);
    
}
