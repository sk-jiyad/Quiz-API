package com.jiyad.controller;

import com.jiyad.model.Question;
import com.jiyad.model.QuestionResponse;
import com.jiyad.model.QuestionWrapper;
import com.jiyad.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String diff, @RequestParam int numQ, @RequestParam String title){
        return quizService.createQuiz(diff, numQ, title);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable int id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submit(@PathVariable Integer id, @RequestBody List<QuestionResponse> questionResponses){
        return quizService.submitQuiz(id, questionResponses);

    }
}
