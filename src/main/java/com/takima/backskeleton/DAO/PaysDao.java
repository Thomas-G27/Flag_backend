package com.takima.backskeleton.DAO;

import com.takima.backskeleton.models.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaysDao extends JpaRepository<Pays,Long> {
    List<Pays> findDistinctByLanguages_NameIgnoreCase(String name);
    List<Pays> findByContinent_NameIgnoreCase(String continentName);
    List<Pays> findByContinentIgnoreCase(String continent);
    @Query("""
select distinct p
  from Pays p
  join p.languages l
  where lower(l.name) = lower(:lang)
    and lower(p.continent.name) = lower(:continent)
""")
    List<Pays> findByLanguageAndContinent(@Param("lang") String lang,
                                          @Param("continent") String continent);}
