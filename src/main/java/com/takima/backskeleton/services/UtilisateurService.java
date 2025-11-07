package com.takima.backskeleton.services;


import com.takima.backskeleton.DAO.UtilisateurDao;
import com.takima.backskeleton.models.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtilisateurService {
    private final UtilisateurDao utilisateurDao;

    public List<Utilisateur> findAll() {
        return utilisateurDao.findAll();
    }

    public Optional<Utilisateur> findById(Long id) {
        return utilisateurDao.findById(id);
    }

    public Optional<Utilisateur> findByName(String name) {
        return utilisateurDao.findByName(name);
    }
}
