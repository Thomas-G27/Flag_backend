package com.takima.backskeleton.DAO;

import com.takima.backskeleton.models.Partie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartieDao extends JpaRepository<Partie, Long> {

    // Lister toutes les parties
    @Query("""
        select distinct pr
        from Partie pr
        left join fetch pr.pays p
    """)
    List<Partie> findAllWithPays();

    // Récupérer une partie par id
    @Query("""
        select pr
        from Partie pr
        left join fetch pr.pays p
        where pr.id = :id
    """)
    Partie findOneWithPays(Long id);
    @Query("""
    select distinct partie from Partie partie
    left join fetch partie.questions q
    where partie.id = :id
  """)
    Optional<Partie> findOneWithQuestions(@Param("id") Long id);

    @Query("""
    select distinct partie from Partie partie
    left join fetch partie.questions q
  """)
    List<Partie> findAllWithQuestions();
}

