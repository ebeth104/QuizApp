package com.example.lisa.QuizApp.service;

import com.example.lisa.QuizApp.models.Question;
import com.example.lisa.QuizApp.dao.QuestionDao;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDao.save(question);
            return new ResponseEntity<>("success",HttpStatus.CREATED);
        } catch(DataAccessException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Database Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public Question getQuestionById(int id) {
        return questionDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));
    }

    public ResponseEntity<String> deleteQuestion(int id) {
        try {
            Question question = getQuestionById(id);
            questionDao.delete(question);
            return new ResponseEntity<>("Deleted successfully", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> updateQuestion(int id, Question updatedQuestion) {
        try {
            Question question = getQuestionById(id);
            question.setTitle(updatedQuestion.getTitle());
            question.setOption1(updatedQuestion.getOption1());
            question.setOption2(updatedQuestion.getOption2());
            question.setOption3(updatedQuestion.getOption3());
            question.setOption4(updatedQuestion.getOption4());
            question.setRightAnswer(updatedQuestion.getRightAnswer());
            question.setCategory(updatedQuestion.getCategory());
            question.setDifficultyLevel(updatedQuestion.getDifficultyLevel());
            questionDao.save(question);
            return new ResponseEntity<>("success",HttpStatus.CREATED);
        } catch(DataAccessException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Database Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
   
