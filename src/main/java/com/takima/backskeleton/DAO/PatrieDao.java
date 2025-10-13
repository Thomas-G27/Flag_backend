package com.takima.backskeleton.DAO;

import com.takima.backskeleton.models.Partie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatrieDao  extends JpaRepository<Partie,Long> {
}
