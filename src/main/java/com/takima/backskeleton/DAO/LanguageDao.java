package com.takima.backskeleton.DAO;

import com.takima.backskeleton.models.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface LanguageDao extends JpaRepository<Language, Long> {
    Optional<Language> findByNameIgnoreCase(String name);
}
