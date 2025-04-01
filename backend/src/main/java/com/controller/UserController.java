package com.fileshare.controller;

import com.fileshare.model.User;
import com.fileshare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestParam String username, @RequestParam String password) {
        return userService.registerUser(username, password);
    }

    @PostMapping("/login")
    public User login(@RequestParam String username, @RequestParam String password) {
        return userService.loginUser(username, password);
    }
}
