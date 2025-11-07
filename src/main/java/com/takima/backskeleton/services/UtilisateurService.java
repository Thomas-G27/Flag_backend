package com.takima.backskeleton.services;


import com.takima.backskeleton.DAO.UtilisateurDao;
import com.takima.backskeleton.DTO.UtilisateurCreateDto;
import com.takima.backskeleton.models.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UtilisateurDao utilisateurDao;
    private final PasswordEncoder passwordEncoder;

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
        utilisateur.set_admin(utilisateurCreateDto.getIsAdmin());

        utilisateurDao.save(utilisateur);
    }

    public void deleteUtilisateur(Utilisateur utilisateur) {
        utilisateurDao.delete(utilisateur);
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
