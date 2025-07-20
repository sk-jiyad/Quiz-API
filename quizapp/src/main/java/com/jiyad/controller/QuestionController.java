package com.jiyad.controller;

import com.jiyad.model.Question;
import com.jiyad.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/question")

public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
         return questionService.getAllQuestions();
    }
    @GetMapping("/difficulty_level/{level}")
    public ResponseEntity<List<Question>> level(@PathVariable String level) {
        return questionService.getQuestionsByDifficulty(level);
    }
    @PostMapping("/post")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
            return questionService.addQuestion(question);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteQuestion(@RequestBody int id){
         return questionService.deleteQuestion(id);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable Integer id, @RequestBody Question updatedQuestion){
        Optional<Question> optional = questionService.getQuestionById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found with id: " + id);
        }
        Question existing = optional.get();

        existing.setQuestionTitle(updatedQuestion.getQuestionTitle()==null ? existing.getQuestionTitle() : updatedQuestion.getQuestionTitle());
        existing.setDifficultyLevel(updatedQuestion.getDifficultyLevel()==null ? existing.getDifficultyLevel(): updatedQuestion.getDifficultyLevel());
        if(updatedQuestion.getOption1()!=null)
            existing.setOption1(updatedQuestion.getOption1());
        if(updatedQuestion.getOption2()!=null)
            existing.setOption2(updatedQuestion.getOption2());
        if(updatedQuestion.getOption3()!=null)
            existing.setOption3(updatedQuestion.getOption3());
        if(updatedQuestion.getOption4()!=null)
            existing.setOption4(updatedQuestion.getOption4());
        if(updatedQuestion.getRightAnswer()!=null)
            existing.setRightAnswer(updatedQuestion.getRightAnswer());

        questionService.addQuestion(existing);
        return ResponseEntity.ok("Question updated successfully!");
    }
}

