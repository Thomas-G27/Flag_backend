package com.takima.backskeleton.services;

import com.takima.backskeleton.DAO.LanguageDao;
import com.takima.backskeleton.models.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageDao languageDao;

    public List<Language> findAll() {
        List<Language> list = new ArrayList<>();
        languageDao.findAll().forEach(list::add);
        return list;
    }

    public Optional<Language> findById(Long id) {
        return languageDao.findById(id);
    }

    public Optional<Language> findByName(String name) {
        return languageDao.findByName(name);
    }
}
