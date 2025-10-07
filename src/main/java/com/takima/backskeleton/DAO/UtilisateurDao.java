package com.takima.backskeleton.DAO;

import com.takima.backskeleton.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurDao extends JpaRepository<Utilisateur,Long> {
}
