package com.example.lisa.QuizApp.controller;

import com.example.lisa.QuizApp.models.QuestionWrapper;
import com.example.lisa.QuizApp.models.Response;
import com.example.lisa.QuizApp.models.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.lisa.QuizApp.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/quizzes")
@CrossOrigin(origins="http://localhost:5173")
public class QuizController {

    @Autowired
    QuizService quizService;
    @PostMapping("createQuiz")
    public ResponseEntity<String> createQuiz(@RequestParam String category,
                                                     @RequestParam int numQ, @RequestParam String title) {
        return quizService.createQuiz(category, numQ, title);
    }

    @GetMapping("getQuiz/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id){
        return quizService.getQuizQuestions(id);
    }

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }
    @PutMapping("updateQuiz/{id}")
    public Quiz updateQuiz(@PathVariable int id, @RequestBody Quiz quiz) {
        return quizService.updateQuiz(id, quiz);
    }

    @DeleteMapping("deleteQuiz/{id}")
        public ResponseEntity<String> deleteQuiz(@PathVariable int id){
        return quizService.deleteQuiz(id);
    }

    @PostMapping("submitQuiz/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id, responses);
    }
}
