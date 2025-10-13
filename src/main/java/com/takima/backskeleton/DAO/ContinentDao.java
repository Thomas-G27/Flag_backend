package com.takima.backskeleton.DAO;

import com.takima.backskeleton.models.Continent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContinentDao extends JpaRepository<Continent,Long> {
}
