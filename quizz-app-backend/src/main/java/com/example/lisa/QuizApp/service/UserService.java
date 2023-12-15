package com.example.lisa.QuizApp.service;

import com.example.lisa.QuizApp.dao.UserDao;
import com.example.lisa.QuizApp.models.Users;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Users getUserById(int id) {
        return userDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public Users createUser(Users user) {
        return userDao.save(user);
    }

    public Users updateUser(int id, Users updatedUser) {
        Users user = getUserById(id);
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setEmail(updatedUser.getEmail());
        user.setAdmin(updatedUser.isAdmin());
        return userDao.save(user);
    }

    public void deleteUser(int id) {
        Users user = getUserById(id);
        userDao.delete(user);
    }

    public List<Users> getAllUsers() {
        return userDao.findAll();
    }

}
