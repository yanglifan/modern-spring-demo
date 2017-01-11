package com.github.yanglifan.demo.modernspring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class ModernSpringDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModernSpringDemoApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args ->
                Arrays.asList("tom", "jerry").forEach(name -> userRepository.save(new User(name)));
    }
}
