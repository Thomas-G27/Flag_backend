package com.takima.backskeleton.services;

import com.takima.backskeleton.DAO.ContinentDao;
import com.takima.backskeleton.DAO.CountryDao;
import com.takima.backskeleton.DAO.LanguageDao;
import com.takima.backskeleton.models.Continent;
import com.takima.backskeleton.models.Country;
import com.takima.backskeleton.models.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryDao countryDao;
    private final ContinentDao continentDao;
    private final LanguageDao languageDao;

    private final String API_URL = "https://cdn.simplelocalize.io/public/v1/countries";

    public void fillDatabaseFromAPI() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Map<String, Object>>> response =
                restTemplate.exchange(API_URL, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {});

        List<Map<String, Object>> data = response.getBody();
        if (data == null) {
            System.out.println("Aucune donnée récupérée depuis l’API !");
            return;
        }

        for (Map<String, Object> countryData : data) {
            String countryName = (String) countryData.get("name");
            String continentName = (String) countryData.get("continent");
            String flag = (String) countryData.get("code");

            if (countryName == null || continentName == null) continue;

            // --- Continent ---
            Continent continent = continentDao.findByName(continentName)
                    .orElseGet(() -> {
                        Continent c = new Continent();
                        c.setName(continentName);
                        return continentDao.save(c);
                    });

            // --- Country ---
            Country country = new Country();
            country.setName(countryName);
            country.setFlag(flag);
            country.setContinent(continent);

            // --- Languages ---
            List<Map<String, Object>> langs = (List<Map<String, Object>>) countryData.get("languages");
            if (langs != null) {
                for (Map<String, Object> langData : langs) {
                    String langName = (String) langData.get("name");
                    String iso639_1 = (String) langData.get("iso_639_1");

                    if (langName == null) continue;

                    Language language = languageDao.findByName(langName)
                            .orElseGet(() -> {
                                Language l = new Language();
                                l.setName(langName);
                                l.setIso639_1(iso639_1);
                                return languageDao.save(l);
                            });

                    country.getLanguages().add(language);
                }
            }

            countryDao.save(country);
        }

        System.out.println("Données importées : " + countryDao.count() + " pays\n Languages : " + languageDao.count() + " langues\n Continents : " + continentDao.count() + " continents" );
    }

    public List<Country> findAll() {
        List<Country> list = new ArrayList<>();
        countryDao.findAll().forEach(list::add);
        return list;
    }

    public Optional<Country> findById(Long id) {
        return countryDao.findById(id);
    }
}
