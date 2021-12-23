package com.example.api.demoapi.service;

import com.example.api.demoapi.dao.UserTestDAO;
import com.example.api.demoapi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {

    @Autowired
    @Qualifier("mysql")
    private UserTestDAO userTestDAO;

    public Collection<User> getAllStudents(){
        return this.userTestDAO.getAllUsers();
    }

    public User getStudentById(int id){
        return this.userTestDAO.getUserById(id);
    }

    public void removeStudentById(int id) {
        this.userTestDAO.removeUserById(id);
    }

    public void updateStudent(User student){
        this.userTestDAO.updateUser(student);
    }

    public void insertStudent(User student) {
        this.userTestDAO.insertUserToDb(student);
    }

}
