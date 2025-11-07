package com.takima.backskeleton.DAO;

import com.takima.backskeleton.models.Game;
import com.takima.backskeleton.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameDao extends JpaRepository<Game, Long> {
    Optional<Game> findByCategorie(String categorie);
    List<Game> findByUtilisateur(Utilisateur utilisateur);
}
