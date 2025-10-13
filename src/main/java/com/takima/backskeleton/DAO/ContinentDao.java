package com.takima.backskeleton.DAO;

import com.takima.backskeleton.models.Continent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContinentDao extends JpaRepository<Continent,Long> {
    Optional<Continent> findByName(String name);
}
