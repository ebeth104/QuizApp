package com.example.lisa.QuizApp.service;

import com.example.lisa.QuizApp.models.Question;
import com.example.lisa.QuizApp.models.QuestionWrapper;
import com.example.lisa.QuizApp.models.Quiz;
import com.example.lisa.QuizApp.dao.QuizDao;
import com.example.lisa.QuizApp.dao.QuestionDao;
import com.example.lisa.QuizApp.models.Response;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public Quiz getQuizById(int id) {
        return quizDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Quiz not found"));
    }

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setCategory(category);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for (Question q : questionsFromDB) {
            QuestionWrapper qw = new QuestionWrapper(Math.toIntExact(q.getId()), q.getTitle(), q.getOption1(),
                    q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qw);
        }
        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int right = 0;
        int i = 0;
        for (Response response : responses) {
            if (response.getResponse().equals(questions.get(i).getRightAnswer()))
                right++;
            i++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }

    public List<Quiz> getAllQuizzes() {
        return quizDao.findAll();
    }

    public Quiz updateQuiz(int id, Quiz updatedQuiz) {
        Quiz quiz = getQuizById(id);
        quiz.setTitle(updatedQuiz.getTitle());
        quiz.setCategory(updatedQuiz.getCategory());
        return quizDao.save(quiz);
    }

    public ResponseEntity<String> deleteQuiz(int id) {
        Quiz quiz = getQuizById(id);
        // Perform any necessary validation or business logic
        quizDao.delete(quiz);
        return new ResponseEntity<>("Successfully deleted", HttpStatus.NO_CONTENT);
    }
}
