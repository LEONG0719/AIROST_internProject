package com.project.airost.service;

import com.project.airost.domain.User;

import java.util.List;

public interface IUserService {

    List<User> getUserList ();

    String createUser(User user);

    String deleteUser(Long id);

    //search and get user
    List <User> searchUser(String name);
}
