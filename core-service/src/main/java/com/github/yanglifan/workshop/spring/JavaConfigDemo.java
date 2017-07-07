package com.github.yanglifan.workshop.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yang Lifan
 */
@Configuration
public class JavaConfigDemo implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaConfigDemo.class);

    @Autowired
    UserHolder userHolder;

    @Autowired
    UserHolder userHolder2;

    @Bean
    public User user() {
        return new User();
    }

    @Bean
    public UserHolder userHolder() {
        return new UserHolder(user());
    }

    @Bean
    public UserHolder userHolder2() {
        return new UserHolder(user());
    }

    @Override
    public void run(String... args) throws Exception {
        userHolder.user.name = "Tom";
        LOGGER.info("userHolder2.user.name => {}", userHolder2.user.name);
    }

    class User {
        String name;
    }

    class UserHolder {
        User user;

        public UserHolder(User user) {
            this.user = user;
        }
    }
}
