package com.github.yanglifan.demo.modernspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.Arrays;

@SpringBootApplication
public class ModernSpringDemoApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModernSpringDemoApplication.class);

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

    @Bean
    CommandLineRunner printProperties(FooProperties fooProperties) {
        return args -> {
            LOGGER.info("foo.enabled={}", fooProperties.isEnabled());
            LOGGER.info("foo.remoteAddress={}", fooProperties.getRemoteAddress());
        };
    }

    @ConfigurationProperties("foo")
    @Component
    static class FooProperties {
        private boolean enabled;
        private InetAddress remoteAddress;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public InetAddress getRemoteAddress() {
            return remoteAddress;
        }

        public void setRemoteAddress(InetAddress remoteAddress) {
            this.remoteAddress = remoteAddress;
        }
    }
}