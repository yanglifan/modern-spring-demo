package com.github.yanglifan.demo.modernspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

@Component
public class DemoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoService.class);

    private final UserRepository userRepository;

    @Value("${foo.enabled}")
    private boolean enabled;

    public DemoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        LOGGER.info("Get a property by @Value foo.enabled={}", enabled);
    }

    @Transactional
    public void saveForDemoHibernateDynamicUpdate() {
        LOGGER.info("Start to save...");
        User stark = userRepository.findByName("stark");
        stark.setAge(8);
        userRepository.save(stark);
    }

    @Transactional
    public void saveUserWithException(String username, Exception ex) throws Exception {
        Assert.notNull(ex);

        User user = new User();
        user.setName(username);
        user.setAge(20);
        userRepository.save(user);

        throw ex;
    }
}
