package com.example.lisa.QuizApp.dao;

import com.example.lisa.QuizApp.models.Question;
import com.example.lisa.QuizApp.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer> {


}
