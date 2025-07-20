package com.jiyad.dao;

import com.jiyad.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question,Integer> {
    List<Question> findByDifficultyLevelIgnoreCase(String difficultyLevel);

    List<Question> findAllByOrderByIdAsc();

    @Query(value = "SELECT * FROM question q Where q.difficulty_level=:diff ORDER BY RANDOM() LIMIT :numQ " , nativeQuery = true)
    List<Question> findRandomQuestionsByDiff(String diff, int numQ);
}
