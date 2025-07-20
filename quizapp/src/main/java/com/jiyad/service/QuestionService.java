package com.jiyad.service;

import com.jiyad.dao.QuestionDao;
import com.jiyad.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try{
            return new ResponseEntity<>(questionDao.findAllByOrderByIdAsc(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<List<Question>> getQuestionsByDifficulty(String level) {
        try {
            return new ResponseEntity<>(questionDao.findByDifficultyLevelIgnoreCase(level), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDao.save(question);
            return new ResponseEntity<>("Success", HttpStatus.CREATED) ;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> deleteQuestion(int id) {
        try{
            if(questionDao.existsById(id)){
                questionDao.deleteById(id);
                return new ResponseEntity<>("Success", HttpStatus.OK) ;
            }
            else{
                return new ResponseEntity<>("Question with id " + id + " is not found.", HttpStatus.NOT_FOUND );
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error Deleting Question", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

//    public String updateQuestion(Question question) {
//
//    }

    public Optional<Question> getQuestionById(Integer id) {
        return questionDao.findById(id);
    }
}
