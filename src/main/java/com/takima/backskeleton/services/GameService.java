package com.takima.backskeleton.services;

import com.takima.backskeleton.DAO.GameDao;
import com.takima.backskeleton.models.Game;
import com.takima.backskeleton.models.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameDao gameDao;

    public List<Game> findAll() {
        return gameDao.findAll();
    }

    public Optional<Game> findById(Long id) {
        return gameDao.findById(id);
    }

    public List<Game> findByUtilisateur(Utilisateur utilisateur) {
        return gameDao.findByUtilisateur(utilisateur);
    }
}
