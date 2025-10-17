package com.takima.backskeleton.services;

import com.takima.backskeleton.DAO.ContinentDao;
import com.takima.backskeleton.DAO.LanguageDao;
import com.takima.backskeleton.DAO.PaysDao;
import com.takima.backskeleton.models.Continent;
import com.takima.backskeleton.models.Language;
import com.takima.backskeleton.models.Pays;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaysService {

    private final PaysDao paysDao;
    private final ContinentDao continentDao;
    private final LanguageDao languageDao;
    private final RestTemplate restTemplate;

    private static final String API_URL = "https://cdn.simplelocalize.io/public/v1/countries";

    /**
     * ici nous avons récupère les pays depuis l'API et alimente Continent / Pays / Language.
     * - Association des langues par nom uniquement
     */
    @Transactional
    public void remplirPaysETContinent() {
        ResponseEntity<List<Map<String, Object>>> response =
                restTemplate.exchange(API_URL, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Map<String, Object>>>() {});

        List<Map<String, Object>> items = response.getBody();
        if (items == null || items.isEmpty()) {
            System.out.println("Aucun pays récupéré depuis l'API !");
            return;
        }

        for (Map<String, Object> data : items) {
            String nom = (String) data.get("name");
            String drapeau = (String) data.get("flag");
            String continentNom = (String) data.get("continent");
            if (nom == null || continentNom == null) continue;

            // Continent
            Continent continent = continentDao.findByName(continentNom)
                    .orElseGet(() -> {
                        Continent c = new Continent();
                        c.setName(continentNom);
                        return continentDao.save(c);
                    });

            //  Pays
            Pays pays = paysDao.findByNameIgnoreCase(nom).orElseGet(Pays::new);
            pays.setName(nom);
            pays.setDrapeau(drapeau != null ? drapeau : "");
            pays.setContinent(continent);

            //Langues
            Object langsObj = data.get("languages");
            if (langsObj instanceof List<?> list) {
                for (Object e : list) {
                    if (!(e instanceof Map<?, ?> m)) continue;
                    Object nameObj = m.get("name");
                    if (!(nameObj instanceof String langName) || langName.isBlank()) continue;

                    Language lang = languageDao.findByNameIgnoreCase(langName)
                            .orElseGet(() -> {
                                Language l = new Language();
                                l.setName(langName);
                                return languageDao.save(l);
                            });

                    pays.getLanguages().add(lang);
                }
            }

            paysDao.save(pays);
        }

        System.out.println("Données enregistrées : "
                + paysDao.count() + " pays, "
                + continentDao.count() + " continents, "
                + languageDao.count() + " langues.");
    }

    // Renvoie tous les pays avec continent + languages déjà chargés
    public List<Pays> findAll() {
        return paysDao.findAllWithRelations();
    }

    /* --- (Facultatif) méthodes de lecture utiles --- */

    public List<Pays> findByContinent(String continentName) {
        return paysDao.findByContinent_NameIgnoreCase(continentName);
    }

    public List<Pays> findByLanguageName(String languageName) {
        return paysDao.findDistinctByLanguages_NameIgnoreCase(languageName);
    }

    public List<Pays> findByLanguageAndContinent(String language, String continent) {
        return paysDao.findByLanguageAndContinent(language, continent);
    }
}
