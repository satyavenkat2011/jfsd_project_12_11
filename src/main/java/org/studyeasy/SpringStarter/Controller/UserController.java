package org.studyeasy.SpringStarter.Controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.studyeasy.SpringStarter.Model.User;
import org.studyeasy.SpringStarter.Repository.UserRepository;
import org.studyeasy.SpringStarter.service.UserService;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

