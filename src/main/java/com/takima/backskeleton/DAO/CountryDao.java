package com.takima.backskeleton.DAO;

import com.takima.backskeleton.models.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryDao extends CrudRepository<Country, Long> { }
