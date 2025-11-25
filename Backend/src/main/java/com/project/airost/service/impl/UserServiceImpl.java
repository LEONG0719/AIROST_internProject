package com.project.airost.service.impl;

import com.project.airost.domain.User;
import com.project.airost.repository.UserRepository;
import com.project.airost.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public List<User> getUserList() {
        return userRepo.getUserList();
    }

    @Override
    public String createUser(User user) {
        try {
            if (user == null) {
                throw new RuntimeException("user cannot be null");
            }
            if (user.getUserId() == null || user.getUserId() <= 0) {
                throw new RuntimeException("User id cannot be null or 0");
            }
            User result = userRepo.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return "Create failed";
        }

        return "Success";
    }

    @Override
    public String deleteUser(Long id){
        userRepo.deleteById(id);
        return "Success";
    }

    //search and get user
    @Override
    public List <User> searchUser(String name){
       return userRepo.getUserByUsername(name);

    }

}
