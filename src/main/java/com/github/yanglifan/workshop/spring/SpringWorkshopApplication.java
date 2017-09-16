package com.github.yanglifan.workshop.spring;

import com.github.yanglifan.workshop.spring.async.AsyncHttpClient;
import com.github.yanglifan.workshop.spring.store.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;

@SpringBootApplication
public class SpringWorkshopApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringWorkshopApplication.class);
    @Autowired
    private AsyncHttpClient asyncHttpClient;

    public static void main(String[] args) {
        SpringApplication.run(SpringWorkshopApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, OrderRepository orderRepository,
                           DemoService demoService, JdbcTemplate jdbcTemplate) {
        return args -> {
            demoService.saveForDemoHibernateDynamicUpdate();

            jdbcTemplate.query("select name from t_users where name = ?", new Object[]{"stark"},
                    (row, num) -> new User(row.getString("name")))
                    .forEach(user -> LOGGER.info("Query with JdbcTemplate, User: {}", user.getName()));

            testRollbackException(demoService, userRepository);

            asyncHttpClient.getUserByUsername("stark");
        };
    }

    @Bean
    CommandLineRunner printProperties(FooProperties fooProperties) {
        return args -> {
            LOGGER.info("foo.enabled={}", fooProperties.isEnabled());
            LOGGER.info("foo.remoteAddress={}", fooProperties.getRemoteAddress());
        };
    }

    @Bean
    HealthIndicator helloWorldHealthIndicator() {
        return () -> {
            Health.Builder builder = new Health.Builder();
            return builder
                    .up()
                    .withDetail("hello", "world")
                    .build();
        };
    }

    private void testRollbackException(DemoService demoService, UserRepository userRepository) {
        LOGGER.info("Demo Spring @Transactional will only handle RuntimeException");
        String username = "testUser";
        try {
            demoService.saveUserWithException(username, new RuntimeException());
        } catch (Exception e) {
            Assert.isInstanceOf(RuntimeException.class, e);
        }

        User testUser = userRepository.findByName(username);
        Assert.isNull(testUser);

        try {
            demoService.saveUserWithException(username, new Exception());
        } catch (Exception e) {
            Assert.isInstanceOf(Exception.class, e);
        }

        testUser = userRepository.findByName(username);
        Assert.notNull(testUser);
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

    @EnableScheduling
    @Configuration
    static class ScheduledConfig {

    }

    @Configuration
    @EnableWebSecurity
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .inMemoryAuthentication()
                    .withUser("admin").password("admin").roles("ADMIN");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            // @formatter:off
            http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/env").authenticated()
//                  .anyRequest().authenticated()
                    .and()
                    .httpBasic();
            // @formatter:on
        }
    }
}
