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
    CommandLineRunner initDatabase(UserRepository userRepository, OrderRepository orderRepository) {
        return args -> {
            Arrays.asList("stark", "rogers").forEach(name -> userRepository.save(new User(name)));

            User stark = userRepository.findByName("stark");
            User rogers = userRepository.findByName("rogers");
            orderRepository.save(new Order("2017011313591000001", stark.getId()));
            orderRepository.save(new Order("2017011313591000002", rogers.getId()));
        };
    }
}
