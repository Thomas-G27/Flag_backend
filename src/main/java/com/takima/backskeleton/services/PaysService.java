package com.takima.backskeleton.services;

import com.takima.backskeleton.DAO.ContinentDao;
import com.takima.backskeleton.DAO.PaysDao;
import com.takima.backskeleton.models.Continent;
import com.takima.backskeleton.models.Pays;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class PaysService {
    public PaysService(PaysDao paysDao) {
        this.paysDao=paysDao;
    }
    @Autowired
    private PaysDao paysDao;
    @Autowired
    private ContinentDao continentDao;
    private final String API_URL = "https://cdn.simplelocalize.io/public/v1/countries";

    public  void remplirPaysETContinent(){
        RestTemplate restTemplate = new RestTemplate();
        //api renvoie un tableau d'objet json
        ResponseEntity<List<Map<String, Object>>> response =
                restTemplate.exchange(API_URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Map<String, Object>>>() {});

        List<Map<String, Object>> paysList = response.getBody();

        if (paysList == null || paysList.isEmpty()) {
            System.out.println("Aucun pays récupéré depuis l'API !");
            return;
        }

        // Dictionnaire pour regrouper les pays par continent
        Map<String, List<Pays>> continentsMap = new HashMap<>();
        for (Map<String, Object> data : paysList) {
            String nom = (String) data.get("name");
            String drapeau = (String) data.get("flag");
            String continent = (String) data.get("continent");

            if (nom == null || continent == null) continue; // sécurité

            Pays pays = new Pays();
            pays.setName(nom);
            pays.setDrapeau(drapeau != null ? drapeau : "");
            // catégorie sera ajoutée plus tard après création

            continentsMap.computeIfAbsent(continent, c -> new ArrayList<>()).add(pays);
        }
        //Pour chaque continent → créer une catégorie + lier les pays
        for (Map.Entry<String, List<Pays>> entry : continentsMap.entrySet()) {
            String continent = entry.getKey();
            List<Pays> listePays = entry.getValue();

            // Cherche si la catégorie existe déjà
            Continent continent1 = continentDao.findByName(continent)
                    .orElseGet(() -> {
                        Continent newCat = new   Continent();
                        newCat.setName(continent);
                        return continentDao.save(newCat);
                    });

            for (Pays p : listePays) {
                p.setContinent(continent1);
                paysDao.save(p);
            }

            continent1.setPays(listePays);
            continentDao.save(continent1);
        }

        System.out.println("Données enregistrées : " + paysDao.count() + " pays et "
                + continentDao.count() + " catégories !");
    }

    public List<Pays> findAll(){
        Iterable<Pays> paysList = paysDao.findAll();
        List<Pays> listPays = new ArrayList<>();
        paysList.forEach(listPays :: add);
        return listPays;
    }

    public Optional<Pays> findById(Long id) {
        return paysDao.findById(id);
    }

}

