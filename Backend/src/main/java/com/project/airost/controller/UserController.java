package com.project.airost.controller;

import com.project.airost.domain.User;
import com.project.airost.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    public List<User> getUserList () {
        return userService.getUserList();
    }

    @PostMapping("/create")
    public String createUser (@RequestBody User user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/delete")
    public String deleteUser (@RequestBody User user){
        return userService.deleteUser(user.getUserId());
    }

    @GetMapping("/getuser")
    public List<User> searchUser(String username){
        return userService.searchUser(username);
    }


}
