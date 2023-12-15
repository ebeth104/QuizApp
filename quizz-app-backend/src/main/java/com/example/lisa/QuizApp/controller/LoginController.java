package com.example.lisa.QuizApp.controller;

import com.example.lisa.QuizApp.dao.UserDao;
import com.example.lisa.QuizApp.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class LoginController {
    @Autowired
    private UserDao userDao;

    @PostMapping("/signup")
    public ResponseEntity<String> SignUp(@RequestBody Users user) {
        System.out.println("In signup");
        userDao.save(user);
        return new ResponseEntity<>("User Created", HttpStatus.OK);
    }

    @PostMapping("/login")
    public String Login(@RequestBody Map<String, String> credentials ) {
        System.out.println("In Login");
        String username = credentials.get("username");
        String password = credentials.get("password");
        List<Users> userByUname = userDao.findByUsername(username);
        for(Users user: userByUname) {
            if(password.equals(user.getPassword())) {
                if(user.isAdmin()) {
                    return "admin";
                }else {
                    return "user";
                }
            }
        }
        return "invalid";
    }
}
