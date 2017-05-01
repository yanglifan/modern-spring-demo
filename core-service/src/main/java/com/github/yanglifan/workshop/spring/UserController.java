package com.github.yanglifan.workshop.spring;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public User getByName(@PathVariable String name) {
        return userRepository.findByName(name);
    }

    @PostMapping
    public Boolean create(@RequestBody @Valid User user) {
        userRepository.save(user);
        return Boolean.TRUE;
    }
}
