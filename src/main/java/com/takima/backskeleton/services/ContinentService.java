package com.takima.backskeleton.services;

import com.takima.backskeleton.DAO.ContinentDao;
import com.takima.backskeleton.models.Continent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContinentService {

    private final ContinentDao continentDao;

    public List<Continent> findAll() {
        List<Continent> list = new ArrayList<>();
        continentDao.findAll().forEach(list::add);
        return list;
    }

    public Optional<Continent> findById(Long id) {
        return continentDao.findById(id);
    }
}
