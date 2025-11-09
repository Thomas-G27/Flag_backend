package com.takima.backskeleton.security;

import com.takima.backskeleton.DAO.UtilisateurDao;
import com.takima.backskeleton.models.Utilisateur;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurDao utilisateurDao;

    public CustomUserDetailsService(UtilisateurDao utilisateurDao) {
        this.utilisateurDao = utilisateurDao;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Utilisateur user = utilisateurDao.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + name));

        return User.builder()
                .username(user.getName())
                .password(user.getMdp())
                .roles(user.is_admin() ? "ADMIN" : "USER")
                .build();
    }
}
