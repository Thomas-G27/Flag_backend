package com.takima.backskeleton.DAO;

import com.takima.backskeleton.models.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryDao extends CrudRepository<Country, Long> {
    Optional<Country> findByName(String name);
    Optional<Country> findByFlag(String flag);
    Optional<Country> findByCapital(String capital);
}
