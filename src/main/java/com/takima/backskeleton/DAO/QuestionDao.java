package com.takima.backskeleton.DAO;

import com.takima.backskeleton.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionDao extends JpaRepository<Question, Long> {

}
