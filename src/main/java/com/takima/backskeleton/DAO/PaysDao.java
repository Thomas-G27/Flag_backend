package com.takima.backskeleton.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.takima.backskeleton.models.Pays;

@Repository
public interface PaysDao extends JpaRepository<Pays, Long> {


    List<Pays> findDistinctByLanguages_NameIgnoreCase(String languageName);


    List<Pays> findByContinent_NameIgnoreCase(String continentName);


    List<Pays> findByLanguages_Id(Long languageId);
    List<Pays> findByContinent_Id(Long continentId);


    @Query("""
         select distinct p
         from Pays p
         join p.languages l
         where lower(l.name) = lower(:language)
           and lower(p.continent.name) = lower(:continent)
         """)
    List<Pays> findByLanguageAndContinent(@Param("language") String language,
                                          @Param("continent") String continent);


    Optional<Pays> findByNameIgnoreCase(String name);

  // returns the first Pays ordered by id ascending (useful to get the "first" row)
  Optional<Pays> findFirstByOrderByIdAsc();

  // fetch the first Pays and initialize its continent to avoid LazyInitializationException during JSON serialization
  @Query("select p from Pays p left join fetch p.continent where p.id = (select min(p2.id) from Pays p2)")
  Optional<Pays> findFirstWithContinent();
}