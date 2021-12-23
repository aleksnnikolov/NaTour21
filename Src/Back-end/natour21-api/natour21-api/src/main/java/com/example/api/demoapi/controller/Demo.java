package com.example.api.demoapi.controller;

import com.example.api.demoapi.entity.User;
import com.example.api.demoapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/demo")
public class Demo {

    @Autowired
    private UserService userService;

    @GetMapping("/data")
    public Collection<User> getData() {
        return userService.getAllStudents();
    }

}
