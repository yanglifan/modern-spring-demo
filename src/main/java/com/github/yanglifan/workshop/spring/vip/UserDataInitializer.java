package com.github.yanglifan.workshop.spring.vip;

import com.github.yanglifan.workshop.spring.User;
import com.github.yanglifan.workshop.spring.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author Yang Lifan
 */
@Component
public class UserDataInitializer implements InitializingBean {
    private final UserRepository userRepository;

    public UserDataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Arrays.asList("stark", "rogers").forEach(name -> userRepository.save(new User(name)));
    }
}
