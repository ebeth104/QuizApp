package com.example.lisa.QuizApp.dao;

import com.example.lisa.QuizApp.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<Users, Integer> {
    public List<Users> findByUsername(String username);
}
