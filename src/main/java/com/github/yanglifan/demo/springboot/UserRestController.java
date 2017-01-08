package com.github.yanglifan.demo.springboot;

import com.github.yanglifan.demo.springboot.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class UserRestController {

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public User getByName(@PathVariable String username) {
        return new User(username);
    }
}
