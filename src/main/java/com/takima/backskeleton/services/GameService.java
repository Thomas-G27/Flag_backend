package com.takima.backskeleton.services;

import com.takima.backskeleton.DAO.GameDao;
import com.takima.backskeleton.DAO.UtilisateurDao;
import com.takima.backskeleton.DTO.GameCreateDto;
import com.takima.backskeleton.models.Game;
import com.takima.backskeleton.models.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameDao gameDao;
    private final UtilisateurDao utilisateurDao;

    public List<Game> findAll() {
        return gameDao.findAll();
    }

    public Optional<Game> findById(Long id) {
        return gameDao.findById(id);
    }

    public List<Game> findByUtilisateur(Utilisateur utilisateur) {
        return gameDao.findByUtilisateur(utilisateur);
    }

    public List<Game> findByCategorie(String categorie) { return gameDao.findByCategorie(categorie);}

    public void addGame(GameCreateDto gameCreateDto) {
        Utilisateur utilisateur = utilisateurDao.findByName(gameCreateDto.getUtilisateur_name())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé : " + gameCreateDto.getUtilisateur_name()));

        Game game = new Game();
        game.setScore(gameCreateDto.getScore());
        game.setGame_date(new Timestamp(System.currentTimeMillis()));
        game.setCategorie(gameCreateDto.getCategorie());
        game.setUtilisateur(utilisateur);

        gameDao.save(game);
    }

    public void deleteGameById(Long id) {
        gameDao.deleteById(id);
    }

}
