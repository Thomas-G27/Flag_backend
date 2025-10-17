package com.takima.backskeleton.services;
import com.takima.backskeleton.DAO.UtilisateurDao;
import com.takima.backskeleton.models.Utilisateur;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UtilisateurDao utilisateurDao;

    // creqtion d'un utilisateur
    public Utilisateur create(UtilisateurDto utilisateurdto) {
        if (utilisateurdto == null) throw new IllegalArgumentException("Payload vide");
        if (utilisateurdto.getEmail() == null || utilisateurdto.getEmail().isBlank())
            throw new IllegalArgumentException("Email requis");
        if (utilisateurDao.existsByEmailIgnoreCase(utilisateurdto.getEmail()))
            throw new IllegalArgumentException("Email déjà utilisé");

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setName(utilisateurdto.getName());
        utilisateur.setEmail(utilisateurdto.getEmail());
        return utilisateurDao.save(utilisateur);
    }

    public static class UtilisateurDto {
        private String name;
        private String email;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
    //Liste des utilisateurs
    public List<Utilisateur> findAll() {
        return utilisateurDao.findAll();
    }
     //Récupérer un utilisateur par ID
    public Utilisateur findById(Long id) {
        return utilisateurDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec cet id = " + id));
    }

    // Mettre à jour un utilisateur
    public Utilisateur update(Long id, Utilisateur newData) {
        Utilisateur existing = findById(id);
        existing.setName(newData.getName());
        existing.setEmail(newData.getEmail());
        return utilisateurDao.save(existing);
    }

    // Supprimer un utilisateur
    public void delete(Long id) {
        if (!utilisateurDao.existsById(id)) {
            throw new RuntimeException("Utilisateur introuvable pour suppression : id = " + id);
        }
        utilisateurDao.deleteById(id);
    }
}

