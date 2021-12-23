package com.example.api.demoapi.dao;

import com.example.api.demoapi.entity.User;

import java.util.Collection;

public interface UserTestDAO {

    Collection<User> getAllUsers();

    User getUserById(int id);

    void removeUserById(int id);

    void updateUser(User student);

    void insertUserToDb(User student);

}
