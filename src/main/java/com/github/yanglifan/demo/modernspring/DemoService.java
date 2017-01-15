package com.github.yanglifan.demo.modernspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DemoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoService.class);

    @Value("${foo.enabled}")
    private boolean enabled;

    @PostConstruct
    public void init() {
        LOGGER.info("Get a property by @Value foo.enabled={}", enabled);
    }
}
