package com.github.yanglifan.demo.springboot;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class UserRestController {
    private final UserRepository userRepository;

    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public User getByName(@PathVariable String username) {
        return new User(username);
    }

    @PostMapping
    public Boolean create(@RequestBody User user) {
        userRepository.save(user);
        return Boolean.TRUE;
    }
}
