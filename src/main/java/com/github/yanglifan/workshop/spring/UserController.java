package com.github.yanglifan.workshop.spring;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@Api(value = "User APIs", description = "Operations of users")
@RequestMapping("/users")
@RestController
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ApiOperation(value = "Get a user by name", response = User.class)
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public User getByName(@PathVariable String name) {
        LOGGER.info("Get a user by username {}", name);
        return userRepository.findByName(name);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Boolean create(@RequestBody @Valid User user) {
        userRepository.save(user);
        return Boolean.TRUE;
    }

    RestTemplate restTemplate;

    @Async
    public CompletableFuture<User> findUser(String user)
            throws InterruptedException {
        String url = String.format("https://api.github.com/users/%s", user);
        User results = restTemplate.getForObject(url, User.class);
        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(results);
    }
}
