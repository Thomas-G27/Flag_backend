package com.takima.backskeleton.DAO;

import com.takima.backskeleton.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurDao extends JpaRepository<Utilisateur, Long> {
    boolean existsByEmailIgnoreCase(String email);
    Optional<Utilisateur> findByEmailIgnoreCase(String email);

}
