package com.takima.backskeleton.DAO;

import com.takima.backskeleton.models.Language;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LanguageDao extends CrudRepository<Language, Long> {
    Optional<Language> findByName(String name);
}
