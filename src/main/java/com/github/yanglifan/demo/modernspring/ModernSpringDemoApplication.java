package com.github.yanglifan.demo.modernspring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ModernSpringDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModernSpringDemoApplication.class, args);
    }

    @Component
    static class UserDatabaseInitializer implements CommandLineRunner {

        private final UserRepository userRepository;

        public UserDatabaseInitializer(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Override
        public void run(String... args) throws Exception {
            userRepository.save(new User("tom"));
        }
    }
}
