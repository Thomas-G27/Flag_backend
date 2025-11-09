package com.takima.backskeleton.services;


import com.takima.backskeleton.DAO.GameDao;
import com.takima.backskeleton.DAO.UtilisateurDao;
import com.takima.backskeleton.DTO.UtilisateurCreateDto;
import com.takima.backskeleton.DTO.UtilisateurDto;
import com.takima.backskeleton.models.Game;
import com.takima.backskeleton.models.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UtilisateurDao utilisateurDao;
    private final PasswordEncoder passwordEncoder;
    private final GameDao gameDao;

    public List<Utilisateur> findAll() {
        return utilisateurDao.findAll();
    }

    public Optional<Utilisateur> findById(Long id) {
        return utilisateurDao.findById(id);
    }

    public Optional<Utilisateur> findByName(String name) {
        return utilisateurDao.findByName(name);
    }

    public void addUtilisateur(UtilisateurCreateDto utilisateurCreateDto) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setName(utilisateurCreateDto.getName());
        utilisateur.setEmail(utilisateurCreateDto.getEmail());
        utilisateur.setMdp(passwordEncoder.encode(utilisateurCreateDto.getMdp()));
        utilisateur.set_admin(utilisateurCreateDto.getIs_admin());

        utilisateurDao.save(utilisateur);
    }

    public void deleteUtilisateur(Utilisateur utilisateur) {
        utilisateurDao.delete(utilisateur);
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void fillUserAndGameInit(){
        // Ajout de deux users exemples
        Utilisateur utilisateur1 = new Utilisateur();
        utilisateur1.setName("Alice");
        utilisateur1.setEmail("alice@gmail.com");
        utilisateur1.setMdp(passwordEncoder.encode("alice.mdp"));
        utilisateur1.set_admin(true);
        utilisateurDao.save(utilisateur1);

        Utilisateur utilisateur2 = new Utilisateur();
        utilisateur2.setName("Bob");
        utilisateur2.setEmail("bob@gmail.com");
        utilisateur2.setMdp(passwordEncoder.encode("bob.mdp"));
        utilisateur2.set_admin(false);
        utilisateurDao.save(utilisateur2);

        // Ajout de parties exemples
        Game game1 = new Game();
        game1.setScore(76.2f);
        game1.setGame_date(Timestamp.from(Instant.now()));
        game1.setCategorie("world");
        game1.setUtilisateur(utilisateur1);
        gameDao.save(game1);

        Game game2 = new Game();
        game2.setScore(56.2f);
        game2.setGame_date(Timestamp.from(Instant.now()));
        game2.setCategorie("europe");
        game2.setUtilisateur(utilisateur2);
        gameDao.save(game2);

        System.out.println("User et games initialisés");

    }
}
